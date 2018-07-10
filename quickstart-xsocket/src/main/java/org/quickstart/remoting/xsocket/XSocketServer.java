/**
 * 项目名称：quickstart-remoting-xsocket 
 * 文件名：XSocketServer.java
 * 版本信息：
 * 日期：2018年4月22日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.remoting.xsocket;

import java.net.InetAddress;
import java.util.Map;
import java.util.Map.Entry;

import org.xsocket.connection.IConnection.FlushMode;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

/**
 * XSocketServer
 * 
 * 采用XSocket通讯的服务端
 * 
 * @author：yangzl@asiainfo.com
 * @2018年4月22日 下午10:16:49
 * @since 1.0
 */
public class XSocketServer {

    /** 设置当前的端口 */
    private static final int PORT = 8014;

    public static void main(String[] args) throws Exception {
        // 注意其构造方法有多个。一般是使用这种构造方法出来的！
        // 不过要注意一下java.net.InetAddress这个类的使用在初始化的时候需要捕获异常
        // 可能是这个绑定的主机可能不存在之类的异常即UnknowHostNameException
        InetAddress address = InetAddress.getByName("localhost");
        // 创建一个服务端的对象
        IServer srv = new Server(address, PORT, new ServerHandler());
        // 设置当前的采用的异步模式
        srv.setFlushmode(FlushMode.ASYNC);
        try {
            // srv.run();
            // the call will not return
            // ... or start it by using a dedicated thread
            srv.start(); // returns after the server has been started
            System.out.println("服务器" + srv.getLocalAddress() + ":" + PORT);
            Map<String, Class> maps = srv.getOptions();
            if (maps != null) {

                for (Entry<String, Class> entry : maps.entrySet()) {
                    System.out.println("key= " + entry.getKey() + " value =" + entry.getValue().getName());
                }
            }
            System.out.println("日志: " + srv.getStartUpLogMessage());

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
