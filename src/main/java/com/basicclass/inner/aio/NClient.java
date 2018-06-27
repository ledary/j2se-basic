package com.basicclass.inner.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/6
 **/
public class NClient {
    SocketChannel channel;
    Selector selector;

    public void init(){
        try{

            channel = SocketChannel.open(new InetSocketAddress("127.0.0.1",30000));
            selector = Selector.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);

            Scanner scan = new Scanner(System.in);
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                channel.write(Charset.forName("UTF-8").encode(line));
                new ClientThread().start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class ClientThread extends Thread{
        @Override
        public void run() {


            try {
                while (selector.select() > 0) {
                    for (SelectionKey key : selector.selectedKeys()) {
                        selector.selectedKeys().remove(key);

                        if (key.isReadable()) {

                            SocketChannel channel = (SocketChannel) key.channel();
                            channel.configureBlocking(false);
                            channel.register(selector,SelectionKey.OP_READ);
                            ByteBuffer buff = ByteBuffer.allocate(1024);
//                                System.out.println("读取状态");
                            String content = "";
                            while (channel.read(buff) > 0) {
                                System.out.println("读取服务端的数据");
                                channel.read(buff);
                                buff.flip();
                                content += Charset.forName("utf-8").decode(buff);

                            }
                            System.out.println("recive response:" + content);
                            key.interestOps(SelectionKey.OP_READ);

                        }
                    }
                }

            } catch (Exception ee) {
                ee.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        new NTimeClient().init();
    }
}


