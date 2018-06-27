package com.netty.in.action.chapter2;

import com.netty.in.action.chapter2.other.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author WGP
 * @description 应答客户端
 * @date 2018/6/7
 **/
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start()throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();


        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args)throws Exception {
        new EchoClient("127.0.0.1",20000).start();
    }
}
/*
创建Bootstrap对象引导启动客户端
创建EventLoopGroup对象并设置到Bootstrap中，
EventLoopGroup可以看做是线程池，用来处理连接，接受数据，发送数据
创建InetSocketAddress 并设置到Bootstrap中，InetSocketAddress是指定连接的服务器地址
添加一个ChannelHandler 客户端成功连接服务器后会执行。
调用Bootstrap.connect来连接服务器
最后关闭EventLoopGroup释放资源。
 */