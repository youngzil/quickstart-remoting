package org.quickstart.netty.v4x.helloworld;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/9/12 16:58
 */
@Sharable
public class LastChannelDuplexHandler extends ChannelDuplexHandler {


  @Override
  public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {

    System.out.println("LastChannelDuplexHandler channelRead " + ctx.channel().remoteAddress() + " Say : " + msg);

    ctx.fireChannelRead(msg);
    // ctx.writeAndFlush("Received your message:" + msg + "\n");

  }

  @Override
  public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {

    System.out.println("LastChannelDuplexHandler write send : " + msg);

    // super.write(ctx,msg, promise);
    ctx.writeAndFlush(msg, promise);
    // ctx.write(msg);
    // ctx.flush();

  }

}
