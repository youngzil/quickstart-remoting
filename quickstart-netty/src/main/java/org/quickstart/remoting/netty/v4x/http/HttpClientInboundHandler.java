/**
 * 项目名称：quickstart-netty 
 * 文件名：HttpClientInboundHandler.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

/**
 * HttpClientInboundHandler
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午9:29:15
 * @version 1.0
 */
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {
    private ByteBufToBytes reader;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
            if (HttpHeaders.isContentLengthSet(response)) {
                reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(response));
            }
        }

        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            reader.reading(content);
            content.release();

            if (reader.isEnd()) {
                String resultStr = new String(reader.readFull());
                System.out.println("Server said:" + resultStr);

                ctx.close();
            }
        }
    }

}
