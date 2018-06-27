package com.basicclass.inner.aio;

import org.junit.internal.runners.statements.RunAfters;
import sun.util.resources.cldr.aa.CalendarData_aa_ER;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.*;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/4
 **/
public class TimeServer {

    //通道队列，模仿Selector的key循环遍历
    private BlockingQueue<SocketChannel> idleQueue = new
            LinkedBlockingQueue<>();
    //工作队列 处理消息的
    private BlockingQueue<Future<SocketChannel>> workingQueue = new
            LinkedBlockingQueue<>();
    //线程管理器
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    {
        new runThread().start();
    }

    public class TimeServerHandleTask implements Runnable{
        SocketChannel socketChannel;
        ExecutorService executorService;
        //直接声明一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        public TimeServerHandleTask(SocketChannel socketChannel,ExecutorService executorService){
            this.socketChannel = socketChannel;
            this.executorService = executorService;
        }

        @Override
        public void run(){
            try{

                /*
                 byteBuffer 四个属性值. position limit capacity
                 在运行过程中注意这几个变量的变化  position 标识数据的插入位置。而limit标识有效数据的末端。
                 通常调用
                 capacity使用标识整个数组的容量大小

                /*
                read 阻塞方式时，不会返回0 而是一直阻塞进行
                非阻塞方式时，根据网上博客说，read返回0有几种情况：客户端数据发送完毕；channel没有数据刻度，buffer的remaining方法返回0
                 */
                if(socketChannel.read(byteBuffer)>0){
                    while(true){
                        //切换读模式 position 为0  limit为数据写的下一个位置
                        byteBuffer.flip();
                        //remaining返回有效数据的长度，如果有效数据的长度
                        //我觉得这块主要是为了不断的读取客户端的数据，防止客户端的数据没有读完整。
                        //更准确的说，是为了读完  GET CURRENT TIME字符
                        if(byteBuffer.remaining() < "GET CURRENT TIME".length()){
                            //进行压缩，清除已读数据
                            byteBuffer.compact();
                            socketChannel.read(byteBuffer);
                            continue;
                        }
                        //打印buffer的有效数据长度，方便观察buffer的变化
                        int len = byteBuffer.remaining();
                        System.out.println("remaining" + len);
                        byte[] request = new byte[len];
                        byteBuffer.get(request);
                        System.out.println("position" + byteBuffer.position());
                        System.out.println("limit" + byteBuffer.limit());
                        System.out.println("capacity" + byteBuffer.capacity());
                        String questStr = new String(request);
                        byteBuffer.clear();
                        if(!"GET CURRENT TIME".equals(questStr)){
                            socketChannel.write(byteBuffer.put("BAD_REQUEST".getBytes()));
                            byteBuffer.clear();
                        }else{
                            ByteBuffer byteBuffer = this.byteBuffer.put(Calendar.getInstance().getTime().toLocaleString().getBytes());
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
                            byteBuffer.clear();
                        }
                    }
                }
                TimeServerHandleTask currentTask = new TimeServerHandleTask(socketChannel,executorService);
                executorService.submit(currentTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class runThread extends Thread {
        public runThread() {

        }

        @Override
        public void run() {

            while (true) {
                //循环通道队列，模仿Selector 的selectkeys（）方法
                for (int i = 0; i < idleQueue.size(); i++) {
                    //这个poll相当于 堆栈的pop方法。移除该元素
                    SocketChannel socketChannel = idleQueue.poll();
                    //检查通道是否为空
                    if (socketChannel != null) {
                        /*
                        isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
                        isDone方法表示任务是否已经完成，若任务完成，则返回true；
                        get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
                        get(long timeout, TimeUnit unit)用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。
                         */
                        //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  线程只要
                        Future<SocketChannel> result = executor.submit(new TimeServerHandleTask(socketChannel, executor), socketChannel);

                        try {
                            //把通道任务加入到工作队列里
                            workingQueue.put(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                }
                for (int i = 0; i < workingQueue.size(); i++) {
                    Future<SocketChannel> future = workingQueue.poll();
                    if (!future.isDone()) {
                        try {
                            workingQueue.put(future);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    SocketChannel channel = null;
                    try {
                        try {
                            //这个get方法是阻塞的。
                            channel = future.get();
                            idleQueue.put(channel);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } catch (Exception exx) {
                        exx.printStackTrace();
                        try{
                            channel.close();
                        }catch (Exception ee){
                            ee.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    public static void main(String[] args)throws Exception {
        TimeServer timeServer = new TimeServer();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress("127.0.0.1",10000));
        while(true){
//            ServerSocket的accept方法是阻塞的
            //该accept非阻塞方式
            SocketChannel channel = ssc.accept();
            if(channel == null){
                continue;
            }else{
                channel.configureBlocking(false);
                timeServer.idleQueue.add(channel);
            }
        }

    }

}
