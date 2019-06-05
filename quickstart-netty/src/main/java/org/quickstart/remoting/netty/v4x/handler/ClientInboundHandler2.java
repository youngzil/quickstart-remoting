/**
 * 项目名称：quickstart-netty 
 * 文件名：ServerInboundHandler1.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ServerInboundHandler1
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午10:10:02
 * @version 1.0
 */
public class ClientInboundHandler2 extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInboundHandler2.class);

    @Override
    // 读取Client发送的信息，并打印出来
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("ClientInboundHandler2.channelRead: ctx :" + ctx);
        System.out.println("ClientInboundHandler2.channelRead: ctx :" + ctx);
        // 通知执行下一个InboundHandler
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("ClientInboundHandler2.channelReadComplete");
        System.out.println("ClientInboundHandler2.channelReadComplete");
        // ctx.flush();
        ctx.fireChannelReadComplete();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientInboundHandler2 channelActive");

        // 通知执行下一个InboundHandler
        ctx.fireChannelActive();
    }

}
