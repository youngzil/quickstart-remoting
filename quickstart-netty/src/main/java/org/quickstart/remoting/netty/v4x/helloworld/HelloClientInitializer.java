/**
 * 项目名称：quickstart-netty 文件名：HelloClientInitializer.java 版本信息： 日期：2017年1月13日 Copyright youngzil Corporation 2017 版权所有 *
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
 * HelloClientInitializer
 *
 * @version 1.0
 * @author：youngzil@163.com
 * @2017年1月13日 下午3:29:13
 */
public class HelloClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();

    /*
     * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
     *
     * 解码和编码 我将会在下一张为大家详细的讲解。再次暂时不做详细的描述
     *
     * */
    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
    pipeline.addLast("decoder", new StringDecoder());
    pipeline.addLast("encoder", new StringEncoder());

    // 客户端的逻辑
    pipeline.addLast("handler", new HelloClientHandler1());

    // pipeline.addLast(new HelloClientHandler2());

  }
}
