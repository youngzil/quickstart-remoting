/**
 * 项目名称：quickstart-netty 
 * 文件名：ServerOutboundHandler2.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * ServerOutboundHandler2
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午10:13:28
 * @version 1.0
 */
public class ClientOutboundHandler2 extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientOutboundHandler2.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("ClientOutboundHandler2.write");
        System.out.println("ClientOutboundHandler2.write");

        System.out.println("msg=" + msg);

        // 执行下一个OutboundHandler
        super.write(ctx, msg, promise);

    }
}
