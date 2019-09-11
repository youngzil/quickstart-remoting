/**
 * 项目名称：quickstart-netty 
 * 文件名：HelloClientHandler.java
 * 版本信息：
 * 日期：2017年1月13日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HelloClientHandler
 * 
 * @author：youngzil@163.com
 * @2017年1月13日 下午3:29:58
 * @version 1.0
 */
public class HelloClientHandler1 extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("HelloClientHandler1  channelRead0 Server say : " + msg);
        // 通知执行下一个InboundHandler
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HelloClientHandler1 channelActive ");
        super.channelActive(ctx);
    }

    // @Override
    // public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    //     System.out.println("HelloClientHandler1 channelInactive close ");
    //     super.channelInactive(ctx);
    // }
}
