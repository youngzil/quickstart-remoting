/**
 * 项目名称：quickstart-netty 
 * 文件名：ServerOutboundHandler1.java
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
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * ServerOutboundHandler1
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午10:12:34
 * @version 1.0
 */
public class ServerOutboundHandler1 extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ServerOutboundHandler1.class);

    @Override
    // 向client发送消息
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("ServerOutboundHandler1.write");
        System.out.println("ServerOutboundHandler1.write");
        String response = "I am ok!!!!!";
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

}
