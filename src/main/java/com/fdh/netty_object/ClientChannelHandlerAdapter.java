package com.fdh.netty_object;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientChannelHandlerAdapter extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("error："+cause.getMessage());
    }

    /**
     * Netty默认只支持传输ByteBuf或者FileRegion对象，Netty可以很好将字节流和上述灵活转换。
     * 如果用户想传送其他Object需要在ChannelPipeline上添加编解码器。
     * 同时默认情况下netty并不会捕获系统在序列化过程中抛出的异常，如果想捕获需要添加监听器。
     * @param ctx 最终处理者
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user = new User();
        ctx.writeAndFlush(new User());
        ctx.writeAndFlush(new User());
        ctx.writeAndFlush(new User());
        //代表数据结束
        ctx.writeAndFlush(new End());
        /*
         * ChannelFuture表示Channel中异步I/O操作的结果，在netty中所有的I/O操作都是异步的，I/O的调用会直接返回，
         * 可以通过ChannelFuture来获取I/O操作的结果状态。
         */
        ChannelFuture channelFuture = ctx.writeAndFlush(user);
        //捕获异常
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        //异常关闭连接
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("From Server message:"+msg);
    }
}
