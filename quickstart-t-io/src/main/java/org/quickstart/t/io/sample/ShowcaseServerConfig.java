/**
 * 项目名称：quickstart-t-io 
 * 文件名：ShowcaseServerConfig.java
 * 版本信息：
 * 日期：2018年5月13日
 * Copyright youngzil Corporation 2018
 * 版权所有 *
 */
package org.quickstart.t.io.sample;

import org.tio.utils.time.Time;

/**
 * ShowcaseServerConfig 
 *  
 * @author：youngzil@163.com
 * @2018年5月13日 下午8:44:54 
 * @since 1.0
 */
public abstract class ShowcaseServerConfig {
    /**
     * 协议名字(可以随便取，主要用于开发人员辨识)
     */
    public static final String PROTOCOL_NAME = "showcase";
    
    public static final String CHARSET = "utf-8";
    /**
     * 监听的ip
     */
    public static final String SERVER_IP = null;//null表示监听所有，并不指定ip

    /**
     * 监听端口
     */
    public static final int SERVER_PORT = 9326;

    /**
     * 心跳超时时间，单位：毫秒
     */
    public static final int HEARTBEAT_TIMEOUT = 1000 * 60;

    /**
     * ip数据监控统计，时间段
     * @author tanyaowu
     *
     */
    public static interface IpStatDuration {
        public static final Long DURATION_1 = Time.MINUTE_1 * 5;
        public static final Long[] IPSTAT_DURATIONS = new Long[] { DURATION_1 };
    }

}

