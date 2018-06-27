package com.netty.in.official.echo;

import com.netty.in.action.chapter2.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/9
 **/
public class EchoServer {
    //getProperty  获取JVM -D参数的配置  key-value对的形式
    static final boolean SSL =
            System.getProperty("ssl")!=null;
    static final int PORT =
            Integer.parseInt(System.getProperty("port","8007"));
    public void init()throws Exception{
        final SslContext sslCtx;
        if(SSL){
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder
                    .forServer(ssc.certificate(),ssc.privateKey()).build();
        }else{
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workGroup)
                    //指定特定类型的通道
                    .channel(NioServerSocketChannel.class)
                    //设置 TCP连接的参数
                    .option(ChannelOption.SO_BACKLOG,100)
                    //增加处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)throws Exception{
                            ChannelPipeline p = ch.pipeline();
                            if(sslCtx != null){
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(new EchoServerHandler());
                        }
                    });
            //这个 sync是阻塞方法  启动服务
            ChannelFuture f = b.bind(PORT).sync();
            //等待channel完成数据的交互，socket
            f.channel().closeFuture().sync();
        }finally {
            //关闭所有线程
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
