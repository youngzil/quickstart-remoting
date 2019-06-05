/**
 * 项目名称：quickstart-netty 
 * 文件名：StringDecoder.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.multiprotocol;

import java.util.List;

import org.quickstart.remoting.netty.v4x.http.ByteBufToBytes;
import org.quickstart.remoting.netty.v4x.selfprotocol.Person;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * StringDecoder
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午3:24:14
 * @version 1.0
 */
public class StringDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 判断是否是String协议
        byte n = "n".getBytes()[0];
        byte p = in.readByte();
        // 把读取的起始位置重置
        in.resetReaderIndex();
        if (n == p) {
            ByteBufToBytes reader = new ByteBufToBytes(1000);
            String msg = new String(reader.read(in));
            Person person = buildPerson(msg);
            out.add(person);
            // in.release();
        } else {
            ctx.fireChannelRead(in);
        }
    }

    private Person buildPerson(String msg) {
        Person person = new Person();
        String[] msgArray = msg.split(";|:");
        person.setName(msgArray[1]);
        person.setAge(Integer.parseInt(msgArray[3]));
        person.setSex(msgArray[5]);
        return person;
    }
}
