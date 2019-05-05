/**
 * 项目名称：quickstart-remoting-xsocket 
 * 文件名：XSocketClient.java
 * 版本信息：
 * 日期：2018年4月22日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.remoting.xsocket;

import java.io.IOException;

import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

/**
 * XSocketClient
 * 
 * 客户端接收服务端信息 IBlockingConnection：这个的话就是不支持事件回调处理机制的！ // INonBlockingConnection:这个连接支持回调机制
 * 
 * 非阻塞的客户端是能够支持事件处理的方法的。即如果从网络通道中没有取到想要的数据就会自动退出程序
 * 
 * @author：youngzil@163.com
 * @2018年4月22日 下午10:19:48
 * @since 1.0
 */
public class XSocketClient {
    private static final int PORT = 8014;

    public static void main(String[] args) throws IOException {
        // 采用非阻塞式的连接
        INonBlockingConnection nbc = new NonBlockingConnection("localhost", PORT, new ClientHandler());

        // 采用阻塞式的连接
        // IBlockingConnection bc = new BlockingConnection("localhost", PORT);
        // 一个非阻塞的连接是很容易就变成一个阻塞连接
        IBlockingConnection bc = new BlockingConnection(nbc);
        // 设置编码格式
        bc.setEncoding("UTF-8");
        // 设置是否自动清空缓存
        bc.setAutoflush(true);
        // 向服务端写数据信息
        for (int i = 0; i < 2; i++) {
            bc.write(" client | i |love |china !..." + i);
        }
        // 向客户端读取数据的信息
        byte[] byteBuffers = bc.readBytesByDelimiter("|", "UTF-8");
        // 打印服务器端信息
        System.out.println(new String(byteBuffers));
        // 将信息清除缓存，写入服务器端
        bc.flush();
//        bc.close();
        while(true) {
            
        }
    }

}
