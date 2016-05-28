package com.abin.lee.netty.service.server.customer.one;


import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author Chalmers 2016年2月24日 下午2:22:41
 */
public class ServerHandler extends SimpleChannelHandler {

    int count = 1;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        // 对从MyDecoder中传递过来的数据进行处理
        System.out.println((String) e.getMessage() + "  " + count);
        count++;
    }
}