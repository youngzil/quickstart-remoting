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
public class OutboundHandler1 extends ChannelOutboundHandlerAdapter {

  private static Logger logger = LoggerFactory.getLogger(OutboundHandler1.class);

  @Override
  // 向client发送消息
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    logger.info("OutboundHandler1.write");
    /*String response = "I am ok!";
    ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
    encoded.writeBytes(response.getBytes());*/

    System.out.println("OutboundHandler1 write send : " + msg);

    // super.write(ctx,msg, promise);
    ctx.write(msg);
    ctx.flush();
  }

}
