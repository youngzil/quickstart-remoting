/**
 * 项目名称：quickstart-netty 
 * 文件名：BusinessHandler.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.multiprotocol;

import org.quickstart.remoting.netty.v4x.selfprotocol.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * BusinessHandler
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午3:25:41
 * @version 1.0
 */
public class BusinessHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(BusinessHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Person person = (Person) msg;
        logger.info("BusinessHandler read msg from client :" + person);

        System.out.println(person);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
