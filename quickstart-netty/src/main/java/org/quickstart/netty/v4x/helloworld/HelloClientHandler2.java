package org.quickstart.netty.v4x.helloworld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/9/11 19:28
 */
public class HelloClientHandler2 extends ChannelInboundHandlerAdapter {

  private static Logger logger = LoggerFactory.getLogger(HelloClientHandler2.class);

  @Override
  // 读取服务端的信息
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    logger.info("HelloClientIntHandler.channelRead");
    /*ByteBuf result = (ByteBuf) msg;
    byte[] result1 = new byte[result.readableBytes()];
    result.readBytes(result1);
    result.release();*/
    System.out.println("HelloClientHandler2 channelRead Server said:" + msg);
    ctx.close();
  }

  @Override
  // 当连接建立的时候向服务端发送消息 ，channelActive 事件当连接建立的时候会触发
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    logger.info("HelloClientIntHandler.channelActive");
   /* String msg = "Are you ok?";
    ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
    encoded.writeBytes(msg.getBytes());
    ctx.write(encoded);
    ctx.flush();*/
    System.out.println("HelloClientHandler2 channelActive active ");
    super.channelActive(ctx);
  }

  // @Override
  // public void channelInactive(ChannelHandlerContext ctx) throws Exception {
  //   System.out.println("HelloClientHandler2 channelInactive close ");
  //   super.channelInactive(ctx);
  // }

}
