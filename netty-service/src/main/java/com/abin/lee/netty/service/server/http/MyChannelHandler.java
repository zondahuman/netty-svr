package com.abin.lee.netty.service.server.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-4-23
 * Time: 下午9:53
 * To change this template use File | Settings | File Templates.
 */
public class MyChannelHandler extends ChannelInboundHandlerAdapter {
    public MyChannelHandler() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //这里我处理点业务逻辑，跳个芭蕾舞
        ctx.writeAndFlush("跳完了芭蕾舞，给你个王八蛋个通知，我晚上九点回家");
        super.channelRead(ctx, msg);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
