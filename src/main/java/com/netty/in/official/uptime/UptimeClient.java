package com.netty.in.official.uptime;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author WGP
 * @description  IdleStateHandler 检测心跳
 * @date 2018/6/11
 **/
public class UptimeClient {

    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port","8080"));

    static final int RECONNECT_DELAY =
            Integer.parseInt(System.getProperty("reconnectDelay","5"));

    private static final int READ_TIMEOUT = Integer.parseInt(System.getProperty("readTimeout","10"));
    private static final UptimeClientHandler handler = new UptimeClientHandler();
    private static final Bootstrap bs = new Bootstrap();

    public void init()throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        bs.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(HOST,PORT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(READ_TIMEOUT,0,0),handler);
                    }
                });
        bs.connect();
    }

    static void connect() {
        bs.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.cause()!= null){
                    handler.startTime = -1;
                    handler.println("Failed to connect：" + channelFuture.cause());
                }
            }
        });
    }

    public static void main(String[] args) throws Exception{
        new UptimeClient().init();
    }
}
