/**
 * 项目名称：quickstart-netty 
 * 文件名：StringEncoder.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.netty.v4x.coder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * StringEncoder
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午2:19:37
 * @version 1.0
 */
// 把String转换成httpResponse
public class ServerStringEncoder extends ChannelOutboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(ServerStringEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("StringEncoder response to client.");
        String serverMsg = (String) msg;

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(serverMsg.getBytes()));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, Values.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }
}
