/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.server;

import org.quickstart.remoting.xsocket.util.ThreadPoolManager;



/**
 *
 * @author Lindily
 */
/**  
 * 采用XSocket通讯的服务端  
 * @author longgangbai  
 *  
 */
public class XSocketServer {

    public static void main(String[] args) throws Exception {
        ThreadPoolManager tpm=ThreadPoolManager.newInstance();
        tpm.addExecuteTask(new ServerEngine());
    }
}
