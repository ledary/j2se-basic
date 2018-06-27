package com.basicclass.inner.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/4
 **/
public class TimeClient {
    static int connectTimeOut = 3000;
    static ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public static void main(String[] args)throws Exception {
        //打开对服务器的通道
        SocketChannel channel = SocketChannel.open();
        //设置非阻塞方式
        channel.configureBlocking(false);
        channel.connect (new InetSocketAddress ("127.0.0.1",10000));

        //连接开始时间
        long start = System.currentTimeMillis();
        while(!channel.finishConnect()){
            if(System.currentTimeMillis()-start >= connectTimeOut){
                throw new RuntimeException("尝试建立连接超过3秒");
            }
        }
        //如果走到这一步，说明连接建立成功
        while (true){
            //对服务端输出数据
            buffer.put("GET CURRENT TIME".getBytes());
            //转换为读模式
            buffer.flip();
            //想服务端输出buffer
            channel.write(buffer);
            //复位操作 limit置位 position  position置0
            buffer.clear();
            if(channel.read(buffer)>0){
                buffer.flip();
                byte[] response=new byte[buffer.remaining()];
                buffer.get(response);
                System.out.println("reveive response:"+new String(response));
                buffer.clear();
            }
            Thread.sleep(5000);


        }
    }
}
