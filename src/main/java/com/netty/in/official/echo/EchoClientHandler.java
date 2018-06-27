package com.netty.in.official.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/9
 **/
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;


    public EchoClientHandler(){
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for(int i=0;i<firstMessage.capacity();i++){
            firstMessage.writeByte((byte)i);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx){
        //Handler添加到piepline里的时候，会包装成ChannelHandlerContext，然后放到
        ctx.writeAndFlush(firstMessage);
    }

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
