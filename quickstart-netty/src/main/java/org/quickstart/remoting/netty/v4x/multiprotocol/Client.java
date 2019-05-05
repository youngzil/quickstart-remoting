/**
 * 项目名称：quickstart-netty 
 * 文件名：Client.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.multiprotocol;

import org.quickstart.remoting.netty.v4x.selfprotocol.Person;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Client
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午3:26:58
 * @version 1.0
 */
public class Client {
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
                    ch.pipeline().addLast(new PersonEncoder());
                    Person person = new Person();
                    person.setName("guowl");
                    person.setSex("man");
                    person.setAge(30);
                    ch.pipeline().addLast(new ClientInitHandler(person));
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connect("127.0.0.1", 8000);
    }
}
