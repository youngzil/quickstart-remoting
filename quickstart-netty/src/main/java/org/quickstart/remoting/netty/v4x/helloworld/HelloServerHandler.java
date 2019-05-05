/**
 * 项目名称：quickstart-netty 
 * 文件名：HelloServerHandler.java
 * 版本信息：
 * 日期：2017年1月13日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.helloworld;

import java.net.InetAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * HelloServerHandler
 * 
 * @author：youngzil@163.com
 * @2017年1月13日 下午3:24:42
 * @version 1.0
 */
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

        // 返回客户端消息 - 我已经接收到了你的消息
        // 注意:在3.x版本中此处有很大区别。在3.x版本中write()方法是自动flush的。在4.x版本的前面几个版本也是一样的。但是在4.0.9之后修改为WriteAndFlush。普通的write方法将不会发送消息。需要手动在write之后flush()一次
        ctx.writeAndFlush("Received your message:" + msg + "\n");
    }

    /*
     * 
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * 
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        super.channelActive(ctx);
    }
}
