/**
 * 项目名称：quickstart-netty 
 * 文件名：BusinessHandler.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.coder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * BusinessHandler
 * 
 * @author：yangzl@asiainfo.com
 * @2017年1月17日 下午2:18:01
 * @version 1.0
 */
public class BusinessHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(BusinessHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String clientMsg = "client said : " + (String) msg;
        logger.info("BusinessHandler read msg from client :" + clientMsg);
        ctx.write("I am very OK!");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
