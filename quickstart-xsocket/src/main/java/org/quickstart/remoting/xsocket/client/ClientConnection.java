/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;
/**
 *
 * @author Lindily
 */
public class ClientConnection {
    private INonBlockingConnection nbc=null;
    private IBlockingConnection bc=null;
    private ClientConfigure conf = new ClientConfigure();
    public void nonBlockingConnect() {
        
        try {   
            nbc=new NonBlockingConnection(conf.getSERVER_ADDR(), conf.getSERVER_PORT(), new ClientHandler());
            //设置编码格式   
            getNbc().setEncoding("UTF-8");
            //设置是否自动清空缓存   
            getNbc().setAutoflush(true);
            //采用非阻塞式的连接
        } catch (IOException ex) {
            Logger.getLogger(XSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void blockingConnect() {
        
        try {
            bc=new BlockingConnection(conf.getSERVER_ADDR(), conf.getSERVER_PORT());
            //设置编码格式   
            getBc().setEncoding("UTF-8");
            //设置是否自动清空缓存   
            getBc().setAutoflush(true);
            //采用阻塞式的连接   
        } catch (IOException ex) {
            Logger.getLogger(XSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the nbc
     */
    public INonBlockingConnection getNbc() {
        return nbc;
    }

    /**
     * @return the bc
     */
    public IBlockingConnection getBc() {
        return bc;
    }

}
