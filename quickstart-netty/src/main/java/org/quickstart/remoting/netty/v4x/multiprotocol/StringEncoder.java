/**
 * 项目名称：quickstart-netty 
 * 文件名：StringEncoder.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.multiprotocol;

import org.quickstart.remoting.netty.v4x.selfprotocol.Person;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * StringEncoder
 * 
 * @author：yangzl@asiainfo.com
 * @2017年1月17日 下午3:30:51
 * @version 1.0
 */
public class StringEncoder extends MessageToByteEncoder<Person> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
        // 转成字符串：name:xx;age:xx;sex:xx;
        StringBuffer sb = new StringBuffer();
        sb.append("name:").append(msg.getName()).append(";");
        sb.append("age:").append(msg.getAge()).append(";");
        sb.append("sex:").append(msg.getSex()).append(";");
        out.writeBytes(sb.toString().getBytes());
    }
}
