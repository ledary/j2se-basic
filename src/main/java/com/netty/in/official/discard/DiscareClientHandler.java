package com.netty.in.official.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/9
 **/
public class DiscareClientHandler extends SimpleChannelInboundHandler<Object> {

    private ByteBuf content;
    private ChannelHandlerContext ctx;

    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (channelFuture.isSuccess()) {
                generateTraffic();
            } else {
                channelFuture.cause().printStackTrace();
                channelFuture.channel().close();
            }
        }
    };


    private void generateTraffic(){
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        this.ctx = ctx;
        content = ctx.alloc().directBuffer(DiscardClient.SIZE).writeZero(DiscardClient.SIZE);
        generateTraffic();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        content.release();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx,Object msg)throws Exception{
        System.out.println("channelRead0");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }




}
