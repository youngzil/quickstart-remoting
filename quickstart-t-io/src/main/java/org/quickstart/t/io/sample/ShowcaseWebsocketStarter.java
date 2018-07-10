/**
 * 项目名称：quickstart-t-io 
 * 文件名：ShowcaseWebsocketStarter.java
 * 版本信息：
 * 日期：2018年5月13日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.t.io.sample;

import java.io.IOException;

import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

/**
 * ShowcaseWebsocketStarter 
 *  
 * @author：yangzl@asiainfo.com
 * @2018年5月13日 下午8:45:52 
 * @since 1.0
 */
public class ShowcaseWebsocketStarter {

    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;

    /**
     *
     * @author tanyaowu
     */
    public ShowcaseWebsocketStarter(int port, ShowcaseWsMsgHandler wsMsgHandler) throws IOException {
        wsServerStarter = new WsServerStarter(port, wsMsgHandler);

        serverGroupContext = wsServerStarter.getServerGroupContext();
        serverGroupContext.setName(ShowcaseServerConfig.PROTOCOL_NAME);
        serverGroupContext.setServerAioListener(ShowcaseServerAioListener.me);

        //设置ip统计时间段
        serverGroupContext.ipStats.addDurations(ShowcaseServerConfig.IpStatDuration.IPSTAT_DURATIONS);
        //设置ip监控
        serverGroupContext.setIpStatListener(ShowcaseIpStatListener.me);
        //设置心跳超时时间
        serverGroupContext.setHeartbeatTimeout(ShowcaseServerConfig.HEARTBEAT_TIMEOUT);
    }

    /**
     * @param args
     * @author tanyaowu
     * @throws IOException
     */
    public static void start() throws IOException {
        ShowcaseWebsocketStarter appStarter = new ShowcaseWebsocketStarter(ShowcaseServerConfig.SERVER_PORT, ShowcaseWsMsgHandler.me);
        appStarter.wsServerStarter.start();
    }

    /**
     * @return the serverGroupContext
     */
    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }
    
    public static void main(String[] args) throws IOException {
        start();
    }

}
