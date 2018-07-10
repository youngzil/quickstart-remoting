/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.client;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 *
 * @author Lindily
 */
/**  
 * 客户端定义数据的处理类  
 * @author longgangbai  
 *  
 */
public class ClientHandler implements IDataHandler, IConnectHandler, IDisconnectHandler {

    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException, ClosedChannelException,
            MaxReadSizeExceededException {
        String data = nbc.readStringByDelimiter("\r\n");
        System.out.println(data);
        return true;
    }

    /**  
     * 连接的成功时的操作  
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException, MaxReadSizeExceededException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        System.out.println("客户端信息:服务器 " + remoteName + " 已经连接....");
        return true;
    }

    /**  
     * 连接断开时的操作  
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
//        String  remoteName=nbc.getRemoteAddress().getHostName();   
//        System.out.println("客户端信息:服务器 "+remoteName+" 已经断开.");
        return true;
    }
    /**  
     *   
     * 接收到数据库时候的处理  
     */
}
