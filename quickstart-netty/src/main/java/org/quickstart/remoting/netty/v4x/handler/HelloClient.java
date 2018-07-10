/**
 * 项目名称：quickstart-netty 
 * 文件名：HelloClient.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * HelloClient
 * 
 * @author：yangzl@asiainfo.com
 * @2017年1月17日 上午10:14:17
 * @version 1.0
 */
public class HelloClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {

                    // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2、OutboundHandler1
                    ch.pipeline().addLast(new ClientOutboundHandler1());
                    ch.pipeline().addLast(new ClientOutboundHandler2());

                    // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1、InboundHandler2
                    ch.pipeline().addLast(new ClientInboundHandler1());
                    ch.pipeline().addLast(new ClientInboundHandler2());

                    ch.pipeline().addLast(new HelloClientIntHandler());

                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            Thread.sleep(1000);
            f.channel().writeAndFlush("ddd");

            f.channel().closeFuture().sync();

            while (true) {
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HelloClient client = new HelloClient();
        client.connect("127.0.0.1", 8000);
    }
}
