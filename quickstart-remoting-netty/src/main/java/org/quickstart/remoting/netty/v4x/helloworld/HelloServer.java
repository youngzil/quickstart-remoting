/**
 * 项目名称：quickstart-netty 
 * 文件名：HelloServer.java
 * 版本信息：
 * 日期：2017年1月13日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * HelloServer
 * 
 * @author：yangzl@asiainfo.com
 * @2017年1月13日 下午3:17:52
 * @version 1.0
 */
public class HelloServer {
    /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = 7878;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new HelloServerInitializer());

            b.option(ChannelOption.SO_KEEPALIVE, true);// 设置Channel选项配置：
            // b.childAttr(childKey, value);//设置子Channel的属性。当值为null是，属性将被删除。

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
