package com.netty.in.action.chapter2;

import com.netty.in.action.chapter2.other.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author WGP
 * @description 应答式交互
 * @date 2018/6/7
 **/
public class EchoServer {
    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void start()throws Exception{
        //            使用NioEventLoopGroup来处理新连接，接收数据，写数据
        EventLoopGroup group = new NioEventLoopGroup();
        try{
//        创建 ServerBootstrap实例引导绑定和启动服务器
            ServerBootstrap b = new ServerBootstrap();
//            使用InetSocketAddress 服务器监听此端口
            //设置childHandler执行所有的连接请求
            b.group(group).channel(NioServerSocketChannel.class)
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast(new EchoServerHandler());
                       }
                   });
            ChannelFuture f = b.bind("127.0.0.1",port).sync();
            System.out.println(EchoServer.class.getName() + "started and listen on" + f.channel().localAddress());
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args)throws Exception {
        new EchoServer(20000).start();
    }
}

/*
创建ServerBootstrap对象，使用NIO，所以指定NioEventLoopGroup
来接受处理新连接，指定通道类型为NioServerSocketChannel 设置InetSocketAddress让
服务器监听某端口，等待客户端的连接

调用childHandler来指定连接后电泳的ChannelHandler
传入ChannelInitializer类型的参数，Channellnitializer为抽象类，需要实现initChannel方法，这个方法就是用来设置ChannelHandler。
最后绑定服务器等待绑定完成，sync方法阻塞知道服务器完成绑定。


最后绑定服务器等待直到绑定完成，调用sync()方法会阻塞直到服务器完成绑定，然后服务器等待通道关闭，因为使用sync()，所以关闭操作也
会被阻塞。现在你可以关闭EventLoopGroup和释放所有资源，包括创建的线程。
这个例子中使用NIO，因为它是目前最常用的传输方式，你可能会使用NIO很长时间，但是你可以选择不同的传输实现。例如，这个例子使用
OIO方式传输，你需要指定OioServerSocketChannel。Netty框架中实现了多重传输方式，将再后面讲述。
 */
