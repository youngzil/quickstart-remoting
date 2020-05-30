/**
 * 项目名称：quickstart-netty 
 * 文件名：ByteBufToBytes.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.netty.v4x.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * ByteBufToBytes
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 上午9:29:58
 * @version 1.0
 */
public class ByteBufToBytes {
    private ByteBuf temp;

    private boolean end = true;

    public ByteBufToBytes(int length) {
        temp = Unpooled.buffer(length);
    }

    public void reading(ByteBuf datas) {
        datas.readBytes(temp, datas.readableBytes());
        if (this.temp.writableBytes() != 0) {
            end = false;
        } else {
            end = true;
        }
    }

    public boolean isEnd() {
        return end;
    }

    public byte[] readFull() {
        if (end) {
            byte[] contentByte = new byte[this.temp.readableBytes()];
            this.temp.readBytes(contentByte);
            this.temp.release();
            return contentByte;
        } else {
            return null;
        }
    }

    public byte[] read(ByteBuf datas) {
        byte[] bytes = new byte[datas.readableBytes()];
        datas.readBytes(bytes);
        return bytes;
    }
}
