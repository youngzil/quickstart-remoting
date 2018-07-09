/**
 * 项目名称：quickstart-netty 
 * 文件名：ClientInitHandler.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.selfprotocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ClientInitHandler
 * 
 * @author：yangzl@asiainfo.com
 * @2017年1月17日 下午2:58:03
 * @version 1.0
 */
public class ClientInitHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInitHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("HelloClientIntHandler.channelActive");
        Person person = new Person();
        person.setName("guowl");
        person.setSex("man");
        person.setAge(30);
        ctx.write(person);
        ctx.flush();
    }
}
