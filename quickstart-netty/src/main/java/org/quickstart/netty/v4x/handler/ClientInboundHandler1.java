/**
 * 项目名称：quickstart-netty 
 * 文件名：ServerInboundHandler1.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.netty.v4x.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ServerInboundHandler1
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午10:10:02
 * @version 1.0
 */
public class ClientInboundHandler1 extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInboundHandler1.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("ClientInboundHandler1.channelRead: ctx :" + ctx);
        System.out.println("ClientInboundHandler1.channelRead: ctx :" + ctx);
        // 通知执行下一个InboundHandler
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("ClientInboundHandler1.channelReadComplete");
        System.out.println("ClientInboundHandler1.channelReadComplete");
        // ctx.flush();
        ctx.fireChannelReadComplete();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientInboundHandler1 channelActive");

        // 通知执行下一个InboundHandler
        ctx.fireChannelActive();
    }
}
