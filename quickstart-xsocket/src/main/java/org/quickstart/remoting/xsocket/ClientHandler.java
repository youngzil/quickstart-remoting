/**
 * 项目名称：quickstart-remoting-xsocket 
 * 文件名：ClientHandler.java
 * 版本信息：
 * 日期：2018年4月22日
 * Copyright youngzil Corporation 2018
 * 版权所有 *
 */
package org.quickstart.remoting.xsocket;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 * ClientHandler
 * 
 * 客户端定义数据的处理类
 * 
 * @author：youngzil@163.com
 * @2018年4月22日 下午10:19:09
 * @since 1.0
 */
public class ClientHandler implements IDataHandler, IConnectHandler, IDisconnectHandler {

    /**
     * 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        System.out.println("remoteName " + remoteName + " has connected ！");
        return true;
    }

    /**
     * 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * 
     * 接收到数据库时候的处理
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException {
        String data = nbc.readStringByDelimiter("|");
        nbc.write("--|Client:receive data from server sucessful| -----");
        nbc.flush();
        System.out.println(data);
        return true;
    }

}
