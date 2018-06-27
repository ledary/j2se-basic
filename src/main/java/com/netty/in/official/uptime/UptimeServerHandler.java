package com.netty.in.official.uptime;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/11
 **/
@ChannelHandler.Sharable
public class UptimeServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx,Object msg){
        System.out.println("channelRead0");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

}
