/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.server;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IConnectionTimeoutHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 *
 * @author Lindily
 */
/**  
 * 服务端定义数据的处理类  
 * @author longgangbai  
 *  
 */
public class ServerHandler implements IDataHandler, IConnectHandler, IIdleTimeoutHandler, IConnectionTimeoutHandler, IDisconnectHandler {

    /**  
     * 即当建立完连接之后可以进行的一些相关操作处理。包括修改连接属性、准备资源、等！  
     * 连接的成功时的操作  
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException, MaxReadSizeExceededException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        System.out.println("服务器信息:客户端 " + remoteName + " 已经连接...");
        return true;
    }

    /**  
     * 即如果失去连接应当如何处理？  
     *需要实现 IDisconnectHandler  这个接口  
     * 连接断开时的操作  
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
//        String  remoteName=nbc.getRemoteAddress().getHostName();
//        System.out.println("服务器信息:客户端 "+remoteName+" 已经断开.");
        return true;
    }

    /**  
     * 即这个方法不光是说当接收到一个新的网络包的时候会调用而且如果有新的缓存存在的时候也会被调用。而且  
     *The onData will also be called, if the connection is closed当连接被关闭的时候也会被调用的!  
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException, ClosedChannelException,
            MaxReadSizeExceededException {
        String data = nbc.readStringByDelimiter("\r\n");
        System.out.println("cmd:"+data);
        CmdExecuter ce = new CmdExecuter(data.trim());
        System.out.println("result:" + ce.getResult());
        nbc.write(ce.getResult() + "\r\n");
        nbc.flush();
        return true;
    }

    /**  
     * 请求处理超时的处理事件  
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
        // TODO Auto-generated method stub   
        return true;
    }

    /**  
     * 连接超时处理事件  
     */
    @Override
    public boolean onConnectionTimeout(INonBlockingConnection connection) throws IOException {
        // TODO Auto-generated method stub   
        return true;
    }
}
