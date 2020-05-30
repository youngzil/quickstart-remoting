/**
 * 项目名称：quickstart-netty 
 * 文件名：HelloServer.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.netty.v4x.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * HelloServer
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午10:08:37
 * @version 1.0
 */
public class HelloServer {
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2
                    // OutboundHandler1
                    ch.pipeline().addLast(new ServerOutboundHandler1());
                    ch.pipeline().addLast(new ServerOutboundHandler2());
                    // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1
                    // InboundHandler2
                    ch.pipeline().addLast(new ServerInboundHandler1());
                    ch.pipeline().addLast(new ServerInboundHandler2());
                }
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HelloServer server = new HelloServer();
        server.start(8000);
    }
}
