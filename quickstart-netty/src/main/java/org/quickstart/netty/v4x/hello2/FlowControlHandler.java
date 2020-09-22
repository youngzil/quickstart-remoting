package org.quickstart.netty.v4x.hello2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>描述: [功能描述] </p >
 *
 * @author yangzl
 * @version v1.0
 * @date 2020/9/15 09:48
 */
public class FlowControlHandler extends ChannelInboundHandlerAdapter {

  private static Logger logger = LoggerFactory.getLogger(HelloServerInHandler.class);

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    //获取积压消息个数,如果达到积压阈值，则进行流控。
    ctx.channel().unsafe().outboundBuffer().size();

    // 可以获取发送队列中尚未发送的消息总长度，它的值是实时精确的：获取到积压消息长度之后，就可以根据当前堆内存的使用情况进行动态流控。
    ctx.channel().unsafe().outboundBuffer().totalPendingWriteBytes();

    for (int i = 0; i < 33; i++) {
      ctx.write(msg);
      if (0 == i) {
        ctx.flush();
      }

    }

    System.out.println("the Pending buffer size:" + ctx.channel().unsafe().outboundBuffer().totalPendingWriteBytes());
    System.out.println("the Pending message number:" + ctx.channel().unsafe().outboundBuffer().size());


    ctx.flush();

    System.out.println("the Pending buffer size:" + ctx.channel().unsafe().outboundBuffer().totalPendingWriteBytes());
    System.out.println("the Pending message number:" + ctx.channel().unsafe().outboundBuffer().size());

    new Thread(() -> {
      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (Exception e) {
        logger.info("Exception", e);
      }

      System.out.println("the Pending buffer size:" + ctx.channel().unsafe().outboundBuffer().totalPendingWriteBytes());
      System.out.println("the Pending message number:" + ctx.channel().unsafe().outboundBuffer().size());
    }).start();

    logger.info("FlowControlHandler.channelRead");

  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //ctx.flush();
  }

}
