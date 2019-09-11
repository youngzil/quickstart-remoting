package org.quickstart.remoting.netty.v4x.helloworld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/9/11 19:23
 */
public class OutboundHandler2 extends ChannelOutboundHandlerAdapter {
  private static Logger logger	= LoggerFactory.getLogger(OutboundHandler2.class);

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    logger.info("OutboundHandler2.write");

    System.out.println( "OutboundHandler2 write send : " + msg);

    // 执行下一个OutboundHandler
    super.write(ctx, msg, promise);
  }

}
