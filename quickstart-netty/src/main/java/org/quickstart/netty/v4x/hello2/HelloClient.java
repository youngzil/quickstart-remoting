/**
 * 项目名称：quickstart-netty 文件名：HelloClient.java 版本信息： 日期：2017年1月17日 Copyright youngzil Corporation 2017 版权所有 *
 */
package org.quickstart.netty.v4x.hello2;

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
 * @version 1.0
 * @author：youngzil@163.com
 * @2017年1月17日 上午9:54:42
 */
public class HelloClient {

  public void connect(String host, int port) throws Exception {
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap();
      b.group(workerGroup)//
          .channel(NioSocketChannel.class)//
          .option(ChannelOption.SO_KEEPALIVE, true)//
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(new HelloClientIntHandler());
            }
          });

      // Start the client.
      ChannelFuture f = b.connect(host, port).sync();

      // Wait until the connection is closed.
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
    }

  }

  public static void main(String[] args) throws Exception {
    HelloClient client = new HelloClient();
    client.connect("127.0.0.1", 8000);
  }
}
