/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xsocket.connection.IConnection.FlushMode;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

/**
 *
 * @author Lindily
 */
public class ServerEngine implements Runnable{

    private IServer srv;

    public ServerEngine() {
        try {
            ServerConfigure conf = new ServerConfigure();
            InetAddress address = InetAddress.getByName(conf.getHOST());
            //创建一个服务端的对象   
            srv = new Server(address, conf.getSERVER_PORT(), new ServerHandler());
            //设置当前的采用的异步模式   
            srv.setFlushmode(FlushMode.ASYNC);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startXsocket() {
        try {
            srv.start();
            Map<String, Class> maps = srv.getOptions();
            if (maps != null) {

                for (Entry<String, Class> entry : maps.entrySet()) {
                    System.out.println("key= " + entry.getKey() + " value =" + entry.getValue().getName());
                }
            }
            System.out.println("日志: " + srv.getStartUpLogMessage());
        } catch (IOException ex) {
            Logger.getLogger(ServerEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runXsocket() {
        srv.run();
        Map<String, Class> maps = srv.getOptions();
        if (maps != null) {

            for (Entry<String, Class> entry : maps.entrySet()) {
                System.out.println("key= " + entry.getKey() + " value =" + entry.getValue().getName());
            }
        }
        System.out.println("日志: " + srv.getStartUpLogMessage());
    }

    @Override
    public void run() {
        this.startXsocket();
        
    }
}
