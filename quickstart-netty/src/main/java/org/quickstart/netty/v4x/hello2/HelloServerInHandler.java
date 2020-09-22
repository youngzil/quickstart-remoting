/**
 * 项目名称：quickstart-netty 文件名：HelloServerInHandler.java 版本信息： 日期：2017年1月17日 Copyright youngzil Corporation 2017 版权所有 *
 */
package org.quickstart.netty.v4x.hello2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HelloServerInHandler
 *
 * @version 1.0
 * @author：youngzil@163.com
 * @2017年1月17日 上午9:54:02
 */
// 该handler是InboundHandler类型
public class HelloServerInHandler extends ChannelInboundHandlerAdapter {

  private static Logger logger = LoggerFactory.getLogger(HelloServerInHandler.class);

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


    //获取积压消息个数,如果达到积压阈值，则进行流控。

    ctx.channel().unsafe().outboundBuffer().size();

    // 可以获取发送队列中尚未发送的消息总长度，它的值是实时精确的：获取到积压消息长度之后，就可以根据当前堆内存的使用情况进行动态流控。
    ctx.channel().unsafe().outboundBuffer().totalPendingWriteBytes();


    logger.info("HelloServerInHandler.channelRead");
    ByteBuf result = (ByteBuf) msg;
    byte[] result1 = new byte[result.readableBytes()];
    // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
    result.readBytes(result1);
    String resultStr = new String(result1);
    // 接收并打印客户端的信息
    System.out.println("Client said:" + resultStr);
    // 释放资源，这行很关键
    result.release();

    // 向客户端发送消息
    String response = "I am ok!";
    // 在当前场景下，发送的数据必须转换成ByteBuf数组
    ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
    encoded.writeBytes(response.getBytes());
    ctx.write(encoded);
    ctx.flush();
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }
}
