package org.quickstart.netty.v4x.helloworld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/9/11 19:22
 */
public class HelloServerInboundHandler2 extends ChannelInboundHandlerAdapter {

  private static Logger logger = LoggerFactory.getLogger(HelloServerInboundHandler2.class);

  @Override
  // 读取Client发送的信息，并打印出来
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    logger.info("InboundHandler2.channelRead: ctx :" + ctx);
    /*ByteBuf result = (ByteBuf) msg;
    byte[] result1 = new byte[result.readableBytes()];
    result.readBytes(result1);
    String resultStr = new String(result1);
    System.out.println("Client said:" + resultStr);
    result.release();*/

    System.out.println("InboundHandler2 channelRead receive" + ctx.channel().remoteAddress() + " Say : " + msg);

    // 通知执行下一个InboundHandler
    ctx.fireChannelRead(msg);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    logger.info("InboundHandler2.channelReadComplete");
    System.out.println("InboundHandler2 channelReadComplete ");
    ctx.flush();
  }

}
