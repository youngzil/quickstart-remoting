/**
 * 项目名称：quickstart-netty 
 * 文件名：ServerInboundHandler1.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
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
public class ServerInboundHandler2 extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ServerInboundHandler2.class);

    @Override
    // 读取Client发送的信息，并打印出来
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("ServerInboundHandler2.channelRead: ctx :" + ctx);
        System.out.println("ServerInboundHandler2.channelRead: ctx :" + ctx);
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        String resultStr = new String(result1);
        System.out.println("Client said:" + resultStr);
        result.release();
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("ServerInboundHandler2.channelReadComplete");
        System.out.println("ServerInboundHandler2.channelReadComplete");
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerInboundHandler2 channelActive");

        // 通知执行下一个InboundHandler
        ctx.fireChannelActive();
    }

}
