package com.netty.in.official.uptime;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/11
 **/
@ChannelHandler.Sharable
public class UptimeClientHandler extends SimpleChannelInboundHandler<Object>{
    long startTime = -1;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(startTime <0){
            startTime = System.currentTimeMillis();
        }
        println("Connected to：" + ctx.channel().remoteAddress());

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        println("Sleeping for：" + UptimeClient.RECONNECT_DELAY + 'S');
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                println("Reconnecting to：" + UptimeClient.HOST + ':' + UptimeClient.PORT);
                UptimeClient.connect();
            }
        },UptimeClient.RECONNECT_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        println("Disconnected from：" + ctx.channel().remoteAddress());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(!(evt instanceof IdleStateEvent)){
            return;
        }
        IdleStateEvent e = (IdleStateEvent)evt;
        if(e.state() == IdleState.READER_IDLE){
            println("Disconnecting due to no inbound traffic");
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //如果在5秒内该链上的channelRead方法都没有被触发，就会调用userEventTriggered方法：
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("read0");
    }

    void println(String msg){
        if(startTime < 0){
            System.err.format("[SERVER IS DOWN] %s%n",msg);
        }else{
            System.err.format("[UPTIME: %5ds] %s%n",(System.currentTimeMillis()-startTime)/1000,msg);
        }
    }
}
