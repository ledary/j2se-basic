package com.basicclass.inner.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Calendar;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/6
 **/
public class NServer {
    private Selector selector = null;
    private final int PORT = 30000;
    private final String prolog = "GET CURRENT TIME";
    public void init()throws Exception{

        selector = Selector.open();
        ServerSocketChannel sc = ServerSocketChannel.open();
        sc.bind(new InetSocketAddress("127.0.0.1",PORT));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_ACCEPT);
        while(selector.select()>0){
            for(SelectionKey key:selector.selectedKeys()){
                selector.selectedKeys().remove(key);
                if(key.isAcceptable()){
                    SocketChannel channel = sc.accept();
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ);
                    key.interestOps(SelectionKey.OP_ACCEPT);
                }
                if(key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    String content = "";
                    while(channel.read(buff) >0){
                        buff.flip();
                        channel.read(buff);
                        content += Charset.forName("utf-8").decode(buff);
                    }
                    System.out.println(content);
                    if(prolog.equals(content)){
                        ByteBuffer bb = ByteBuffer.allocate(1024);
                        bb.put(Calendar.getInstance().getTime().toLocaleString().getBytes());
                        bb.flip();
                        channel.write(bb);
                    }
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }

    }

    public static void main(String[] args)throws Exception {
        new NServer().init();
    }
}
