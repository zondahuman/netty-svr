package com.abin.lee.netty.client.four;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class GateWayServer {

    public void connect(int port,String host)throws Exception{

        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_KEEPALIVE, true)// 保持长连接
                    .option(ChannelOption.TCP_NODELAY, false)// nagle算法
                    .option(ChannelOption.SO_SNDBUF, 1 * 1024 * 1024)// 1m
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new GateWayChannelHandler());
                        };
                    });

            //发起异步连接操作
            ChannelFuture f = b.connect(host,port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }finally{
            //退出，释放资源
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args)throws Exception {
        int port = 8085;
        if(args!=null && args.length > 0){
            port = Integer.valueOf(args[0]);
        }
        new GateWayServer().connect(port, "127.0.0.1");
    }
}
