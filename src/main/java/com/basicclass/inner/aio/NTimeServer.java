package com.basicclass.inner.aio;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/6
 **/
public class NTimeServer {
    private final int PORT = 30000;
    String prolog = "GET CURRENT TIME";

    private Selector selector;;
    private ServerSocketChannel serverSocketChannel;

    public void init(){
        try{
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",PORT));
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(selector.select() >0){
                for(SelectionKey selectionKey:selector.selectedKeys()){
                    selector.selectedKeys().remove(selectionKey);
                    if(selectionKey.isAcceptable()){
                        System.out.println("连接状态");

                        try{
                            //该accept不是在阻塞的，而ServerSocket的accept是阻塞的
                            SocketChannel channel = null;
                                channel = serverSocketChannel.accept();
                            channel.configureBlocking(false);
                            channel.register(selector,SelectionKey.OP_READ);
                            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }if(selectionKey.isReadable()){
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        System.out.println("开始接受数据");
                        try{
                            String content = "";
                            while(channel.read(buffer) > 0){
                                channel.read(buffer);
                                buffer.flip();
                                content += Charset.forName("utf-8").decode(buffer);
                            }
                            System.out.println(content);
                            if(prolog.equals(content)){
                                System.out.println("开始传送数据");
                                byte[] bytes = Calendar.getInstance().getTime().toLocaleString().getBytes();
                                ByteBuffer buf = ByteBuffer.allocate(bytes.length);
                                buf.put(bytes);
                                buf.flip();
                                channel.write(buf);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            selectionKey.cancel();
                            if(selectionKey.channel()!=null){
                                try{
                                    selectionKey.channel().close();
                                }catch (Exception ee){
                                    ee.printStackTrace();
                                }
                            }


                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class HandlerThread extends  Thread{
        String prolog = "GET CURRENT TIME";
        SelectionKey selectionKey;
        public HandlerThread(SelectionKey key){
            this.selectionKey = key;
        }
        @Override
        public void run() {
            if(selectionKey.isAcceptable()){
                System.out.println("连接状态");

                try{
                    //该accept不是在阻塞的，而ServerSocket的accept是阻塞的
                    SocketChannel channel = null;
                            while(channel == null){
                                channel = serverSocketChannel.accept();
                            }
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ);
                    selectionKey.interestOps(SelectionKey.OP_ACCEPT);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(selectionKey.isReadable()){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                System.out.println("开始接受数据");
                try{
                    String content = "";
                    while(channel.read(buffer) > 0){
                        buffer.flip();
                        content += Charset.forName("utf-8").decode(buffer);
                    }
                    System.out.println(content);
                    if(prolog.equals(content)){
                        System.out.println("开始传送数据");
                        byte[] bytes = Calendar.getInstance().getTime().toLocaleString().getBytes();
                        ByteBuffer buf = ByteBuffer.allocate(bytes.length);
                        buf.put(bytes);
                        channel.write(buf);
                    }


                }catch (Exception e){
                    e.printStackTrace();
                    selectionKey.cancel();
                    if(selectionKey.channel()!=null){
                        try{
                            selectionKey.channel().close();
                        }catch (Exception ee){
                            ee.printStackTrace();
                        }
                    }


                }
            }
        }
    }

    public static void main(String[] args) {
        new NTimeServer().init();
    }
}
