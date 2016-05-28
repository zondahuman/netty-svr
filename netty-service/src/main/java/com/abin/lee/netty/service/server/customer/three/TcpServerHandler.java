package com.abin.lee.netty.service.server.customer.three;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by abin
 * Be Created in 2016/5/25.
 * 解决拆包问题
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {
    //AttributeKey这里面只定义了一个counter，因为AttributeKey是和channel绑定的，所以多个线程的话也没啥关系
    private AttributeKey<Integer> attributeKey = AttributeKey.valueOf("counter");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Attribute<Integer> attribute = ctx.attr(attributeKey);
        int counter = 1;
        if (attribute.get() == null) {
            attribute.set(1);
        } else {
            counter = attribute.get();
            counter++;
            attribute.set(counter);
        }
        String line = (String) msg;
        System.out.println("第" + counter + "次请求:" + line);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}