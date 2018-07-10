/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.client;

import org.quickstart.remoting.xsocket.util.ThreadPoolManager;

/**
 *
 * @author Lindily
 */
/**  
 * 客户端接收服务端信息  
 * @author lindily
 * IBlockingConnection：这个的话就是不支持事件回调处理机制的！  
 *INonBlockingConnection:这个连接支持回调机制  
 *非阻塞的客户端是能够支持事件处理的方法的。即如果从网络通道中没有取到想要的数据就会自动退出程序  
 */
public class XSocketClient {


    public static void main(String[] args){
        ThreadPoolManager tpm=ThreadPoolManager.newInstance(); 
        tpm.addExecuteTask(new CmdInput());
        
    }
}
