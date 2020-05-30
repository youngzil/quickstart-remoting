/**
 * 项目名称：quickstart-netty 
 * 文件名：PersonEncoder.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.netty.v4x.selfprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * PersonEncoder
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午2:58:45
 * @version 1.0
 */
public class PersonEncoder extends MessageToByteEncoder<Person> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
        byte[] datas = ByteObjConverter.ObjectToByte(msg);
        out.writeBytes(datas);
        ctx.flush();
    }
}
