package com.fdh.netty_string;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * 服务器端
 */
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("error："+cause.getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("From Client :"+buf.toString(CharsetUtil.UTF_8));

        ByteBuf response = Unpooled.buffer();
        String resMes = "I am Server!\t"+new Date().toLocaleString();
        response.writeBytes(resMes.getBytes());
        ChannelFuture channelFuture = ctx.writeAndFlush(response);

        //关闭通道（由服务器进行关闭）
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}
