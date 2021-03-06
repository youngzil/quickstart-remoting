/**
 * 项目名称：quickstart-netty 文件名：HelloServerInitializer.java 版本信息： 日期：2017年1月13日 Copyright youngzil Corporation 2017 版权所有 *
 */
package org.quickstart.netty.v4x.helloworld;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

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

    // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
    pipeline.addLast("in1", new HelloServerInboundHandler1());//入站
    pipeline.addLast("in2", new HelloServerInboundHandler2());//入站
    // 自己的逻辑InboundHandler

    // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
    pipeline.addLast("Duplex1", new LoggingHandler());//入站 和 出站
    pipeline.addLast("Duplex2", new LastChannelDuplexHandler());//入站 和 出站

    pipeline.addLast("out1", new OutboundHandler1());//出站
    pipeline.addLast("out2", new OutboundHandler2());//出站

    pipeline.addLast("in4", new HelloServerInboundHandler4());//入站

    // 最后一个必须是ChannelInboundHandler,如果是ChannelDuplexHandler类型的，只会执行入站逻辑channelRead，不会执行出站逻辑write
    // 如果中间一个InboundHandler直接返回，不往后传递，只有这个hander之前的outhander才会执行,如果最后一个是DuplexHandler，里面的write逻辑也不会执行


  }


}
