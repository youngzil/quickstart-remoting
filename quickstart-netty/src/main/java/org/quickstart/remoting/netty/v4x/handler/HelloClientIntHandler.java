/**
 * 项目名称：quickstart-netty 
 * 文件名：HelloClientIntHandler.java
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
 * HelloClientIntHandler
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午10:14:50
 * @version 1.0
 */
public class HelloClientIntHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HelloClientIntHandler.class);

    @Override
    // 读取服务端的信息
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("HelloClientIntHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        result.release();
        ctx.close();
        System.out.println("Server said:" + new String(result1));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("HelloClientIntHandler.channelReadComplete");
        System.out.println("HelloClientIntHandler.channelReadComplete");
        ctx.flush();
        // ctx.fireChannelReadComplete();
    }

    @Override
    // 当连接建立的时候向服务端发送消息 ，channelActive 事件当连接建立的时候会触发
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("HelloClientIntHandler.channelActive");
        System.out.println("HelloClientIntHandler channelActive");
        String msg = "Are you ok????";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();

        // // 通知执行下一个InboundHandler
        ctx.fireChannelActive();
    }
}
