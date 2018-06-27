package com.netty.in.action.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.ByteBuffer;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/7
 **/

/*
ChannelInboundHandlerAdapter 处理完消息后需要负责释放资源。
调用bYTEBuf.release释放资源。

SimpleChannInboundHandler完成channelRead0方法后释放消息。
Netty处理所有消息的ChannelHandler实现ReferenceCounted接口达到
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    //从服务器接受到数据后发生
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received："
                + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
