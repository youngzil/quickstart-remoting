package org.quickstart.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;

        //tiny规格内存分配  会变成大于等于16的整数倍的数：这里254 会规格化为256
        ByteBuf byteBuf = alloc.directBuffer(254);

        byteBuf.writeInt(126);
        System.out.println(byteBuf.readInt());
    System.out.println("byteBuf=" + byteBuf);
        //很重要，内存释放
        byteBuf.release();

        //走缓存
        byteBuf = alloc.directBuffer(256);

        byteBuf.writeInt(126);
        System.out.println(byteBuf.readInt());
        System.out.println("byteBuf=" + byteBuf);

        // 模拟走另一个分支（继续再chunk上面分配page里面另外的subpage） 所以暂时不释放， 稍后释放，
        //byteBuf.release();

        ByteBuf byteBuf0 = alloc.directBuffer(256);

        byteBuf0.writeInt(126);
        System.out.println(byteBuf0.readInt());

        //进行释放
        byteBuf.release();
        byteBuf0.release();

        //small规格内存分配 会变成大于等于2的幂次方的数：这里2*1023 会规格化为2*1024
        byteBuf = alloc.directBuffer(2 * 1023);

        byteBuf.writeInt(127);
        System.out.println(byteBuf.readInt());
        //很重要，内存释放
        byteBuf.release();

        //走缓存
        byteBuf = alloc.directBuffer(2 * 1024);

        byteBuf.writeInt(127);
        System.out.println(byteBuf.readInt());
        //很重要，内存释放
        byteBuf.release();

        //normal规格内存分配
        byteBuf = alloc.directBuffer(8 * 1024);
        byteBuf.writeInt(128);
        System.out.println(byteBuf.readInt());
        //很重要，内存释放
        byteBuf.release();

        //不走缓存
        byteBuf = alloc.directBuffer(64 * 1024);
        byteBuf.writeInt(128);
        System.out.println(byteBuf.readInt());
        //很重要，内存释放
        byteBuf.release();

        ByteBuf byteBuf1 = Unpooled.buffer(10);
        byteBuf1.release();

        ByteBuf byteBuf2 = Unpooled.directBuffer(20);
        byteBuf2.release();

    }
}