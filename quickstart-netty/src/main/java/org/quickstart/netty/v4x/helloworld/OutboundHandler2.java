package org.quickstart.netty.v4x.helloworld;

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

  private static Logger logger = LoggerFactory.getLogger(OutboundHandler2.class);

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    logger.info("OutboundHandler2.write");

    System.out.println("OutboundHandler2 write send : " + msg);

    // super.write(ctx,msg, promise);

    // 执行下一个OutboundHandler
    ctx.write(msg, promise);
    // 或者
    // ctx.write(msg);
    // 或者
    // super.write(ctx, msg, promise);

  }

}
