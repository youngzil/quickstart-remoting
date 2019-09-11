/**
 * 项目名称：quickstart-netty 文件名：HelloServerInitializer.java 版本信息： 日期：2017年1月13日 Copyright youngzil Corporation 2017 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.helloworld;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * HelloServerInitializer
 *
 * @version 1.0
 * @author：youngzil@163.com
 * @2017年1月13日 下午3:20:06
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();

    // 以("\n")为结尾分割的 解码器
    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

    // 字符串解码 和 编码
    pipeline.addLast("decoder", new StringDecoder());
    pipeline.addLast("encoder", new StringEncoder());

    // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
    ch.pipeline().addLast(new OutboundHandler1());
    ch.pipeline().addLast(new OutboundHandler2());
    // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
    ch.pipeline().addLast(new HelloServerInboundHandler1());
    ch.pipeline().addLast(new HelloServerInboundHandler2());

    // 自己的逻辑Handler
    pipeline.addLast("handler", new HelloServerInboundHandler4());

  }


}
