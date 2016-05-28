package com.abin.lee.netty.service.server.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-4-21
 * Time: 下午9:05
 * To change this template use File | Settings | File Templates.
 */
public class HeartBeatChannelHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(this.getClass()+"--exceptionCaught--ctx="+ctx.name()+"  cause="+cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(this.getClass()+"--userEventTriggered--ctx="+ctx.name()+"  evt="+evt);
        if(IdleStateEvent.class.isAssignableFrom(evt.getClass())){
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state() == IdleState.READER_IDLE)
                System.out.println("read idle");
            else if(event.state() == IdleState.WRITER_IDLE)
                System.out.println("write idle");
            else if(event.state()==IdleState.ALL_IDLE)
                System.out.println("all idle");
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(this.getClass()+"--channelInactive--ctx="+ctx.name());
        ctx.writeAndFlush(this.getClass()+"，这个链接断开了啊");
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(this.getClass()+"--channelActive--ctx="+ctx.name());
        ctx.writeAndFlush(this.getClass()+"，这个链接连上了啊");

        super.channelActive(ctx);
    }
}
