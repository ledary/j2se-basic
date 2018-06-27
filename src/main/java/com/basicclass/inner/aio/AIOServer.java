package com.basicclass.inner.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/3
 **/
public class AIOServer {
    static final int PORT = 30000;
    final static String UTF_8 = "utf-8";

    static List<AsynchronousSocketChannel> channelList = new ArrayList<>();
    public void startListen()throws InterruptedException,Exception{
        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(20);
        //
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup)
                .bind(new InetSocketAddress(PORT));
//        serverSocketChannel.accept(null,new AcceptA);
    }

    class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel,Object>{
        private AsynchronousServerSocketChannel serverChannel;
        public AcceptHandler(AsynchronousServerSocketChannel sc){
            this.serverChannel = sc;
        }

        ByteBuffer buff = ByteBuffer.allocate(1024);

        @Override
        public void completed(AsynchronousSocketChannel sc, Object attachment) {
            AIOServer.channelList.add(sc);
            serverChannel.accept(null,this);
            sc.read(buff, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    buff.flip();
                    String content = StandardCharsets.UTF_8.decode(buff).toString();
                    for(AsynchronousSocketChannel c:AIOServer.channelList){
                        try{
                            c.write(ByteBuffer.wrap(content.getBytes(AIOServer.UTF_8))).get();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }buff.clear();
                    sc.read(buff,null,this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("读取数据失败" + exc);
                    AIOServer.channelList.remove(sc);
                }
            });
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            System.out.println("读取数据失败" + exc);
        }
    }

    public static void main(String[] args)throws Exception {
        AIOServer server = new AIOServer();
        server.startListen();
    }

}
