package org.quickstart.remoting.netty.v4x.helloworld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/9/11 19:21
 */

public class HelloServerInboundHandler1 extends ChannelInboundHandlerAdapter {

  private static Logger logger = LoggerFactory.getLogger(HelloServerInboundHandler1.class);

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    logger.info("InboundHandler1.channelRead: ctx :" + ctx);

    System.out.println("InboundHandler1 channelRead receive" + ctx.channel().remoteAddress() + " Say : " + msg);

    // 通知执行下一个InboundHandler
    // ctx.fireChannelRead(msg);

    // 不往后传递，直接返回数据，不影响OutboundHandler的执行
    ctx.writeAndFlush("HelloServerInboundHandler1 channelRead  send" + msg + "\n");
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    logger.info("InboundHandler1.channelReadComplete");
    System.out.println("InboundHandler1 channelReadComplete ");
    ctx.flush();
  }
}

