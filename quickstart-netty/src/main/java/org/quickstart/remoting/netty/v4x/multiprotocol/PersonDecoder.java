/**
 * 项目名称：quickstart-netty 
 * 文件名：PersonDecoder.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.multiprotocol;

import java.util.List;

import org.quickstart.remoting.netty.v4x.http.ByteBufToBytes;
import org.quickstart.remoting.netty.v4x.selfprotocol.ByteObjConverter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * PersonDecoder
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午3:23:35
 * @version 1.0
 */
public class PersonDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte n = "n".getBytes()[0];
        byte p = in.readByte();
        in.resetReaderIndex();
        if (n != p) {
            // 把读取的起始位置重置
            ByteBufToBytes reader = new ByteBufToBytes(1000);
            out.add(ByteObjConverter.ByteToObject(reader.read(in)));
        } else {
            // 执行其它的decode
            ctx.fireChannelRead(in);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
