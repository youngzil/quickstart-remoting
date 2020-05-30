/**
 * 项目名称：quickstart-netty 
 * 文件名：ClientInitHandler.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.netty.v4x.multiprotocol;

import org.quickstart.netty.v4x.selfprotocol.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ClientInitHandler
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午3:27:40
 * @version 1.0
 */
public class ClientInitHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInitHandler.class);
    private Person person;

    public ClientInitHandler(Person person) {
        this.person = person;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("ClientInitHandler.channelActive");
        ctx.write(person);
        ctx.flush();
    }
}
