/**
 * 项目名称：quickstart-netty 
 * 文件名：StringDecoder.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.coder;

import org.quickstart.remoting.netty.v4x.http.ByteBufToBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

/**
 * StringDecoder
 * 
 * @author：yangzl@asiainfo.com
 * @2017年1月17日 下午2:16:52
 * @version 1.0
 */
public class ServerStringDecoder extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ServerStringDecoder.class);
    private ByteBufToBytes reader;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("StringDecoder : msg's type is " + msg.getClass());
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(request));
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            reader.reading(content.content());

            if (reader.isEnd()) {
                byte[] clientMsg = reader.readFull();
                logger.info("StringDecoder : change httpcontent to string ");
                ctx.fireChannelRead(new String(clientMsg));
            }
        }
    }

}
