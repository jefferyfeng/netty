package com.fdh.netty_string;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 *  (Interface)     ChannelHandler      ( ChannelHandler类似于Servlet的Filter过滤器，负责对I/O事件或者I/O操作进行拦截和处理，
 *                      |                 它可以选择性地拦截和处理自己感兴趣的事件，也可以透传和终止事件的传递。基于ChannelHandler接口，
 *                      |                 用户可以方便地进行业务逻辑定制，例如打印日志、统一封装异常信息、性能统计和消息编解码等。)
 *                      |
 *   (Class)    ChannelHandlerAdapter
 *
 *   客户端
 */
public class ClientChannelHandlerAdapter extends ChannelHandlerAdapter {
    /**
     * 异常时的回调
     * @param ctx 最终处理者
     * @param cause 异常对象
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("error："+cause.getMessage());
    }

    /**
     *  客户端连接服务器时回调
     * @param ctx 最终处理者
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //通过Unpooled提供的静态方式创建. (和ByteBuffer不同ByteBuf缓冲区可以自动扩展和ByteBuffer不同ByteBuf缓冲区可以自动扩展)
        ByteBuf buf = Unpooled.buffer();
        //写入数据到buf中
        buf.writeBytes("I am Client!".getBytes());
        //最终处理者写队列并刷新
        ctx.writeAndFlush(buf);
    }

    /**
     * 客户端收到消息时回调
     * @param ctx 最终处理者
     * @param msg  服务器响应
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("From Server message :" +buf.toString(CharsetUtil.UTF_8));
    }
}
