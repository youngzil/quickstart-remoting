/**
 * 项目名称：quickstart-netty 
 * 文件名：PersonDecoder.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.selfprotocol;

import java.util.List;

import org.quickstart.remoting.netty.v4x.http.ByteBufToBytes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * PersonDecoder
 * 
 * @author：yangzl@asiainfo.com
 * @2017年1月17日 下午2:55:55
 * @version 1.0
 */
public class PersonDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBufToBytes read = new ByteBufToBytes(1000);
        Object obj = ByteObjConverter.ByteToObject(read.read(in));
        out.add(obj);
    }

}
