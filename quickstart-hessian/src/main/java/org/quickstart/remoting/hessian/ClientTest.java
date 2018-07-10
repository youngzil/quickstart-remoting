/**
 * 项目名称：quickstart-remoting-hessian 
 * 文件名：ClientTest.java
 * 版本信息：
 * 日期：2018年5月27日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.remoting.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * ClientTest
 * 
 * @author：yangzl@asiainfo.com
 * @2018年5月27日 上午9:36:47
 * @since 1.0
 */
public class ClientTest {

    // 启动jetty服务：mvn jetty:run

    public static void main(String[] args) throws Exception {
        HessianProxyFactory factory = new HessianProxyFactory();

        String url = "http://127.0.0.1:3389/hessian/user";

        UserService us = (UserService) factory.create(UserService.class, url);

        System.out.println(us.getById(2l));
        System.out.println(us.get());
    }

}
