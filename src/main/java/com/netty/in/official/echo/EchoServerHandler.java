package com.netty.in.official.echo;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/9
 **/
//Shareable可以让同一个Handler添加两次
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    //从重写的方法可以看到，Handler都被转换为ChannelHandlerContext了


    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
