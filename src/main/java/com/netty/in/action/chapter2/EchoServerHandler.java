package com.netty.in.action.chapter2;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author WGP
 * @description 针对不同数据
 * @date 2018/6/7
 **/
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf bb = (ByteBuf) msg;
//        bb.markReaderIndex();
//        System.out.println("Server received: " + ByteBufUtil
//                .hexDump(bb.readBytes(bb.readableBytes())));
//        bb.resetReaderIndex();
//        ctx.write(msg);


        System.out.println("Server received：" + ((ByteBuf)msg).toString(CharsetUtil.UTF_8));
        String response = "来自服务端的数据" + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());
        ctx.writeAndFlush(resp);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
