/**
 * 项目名称：quickstart-remoting-hessian 
 * 文件名：UserHessianServlet.java
 * 版本信息：
 * 日期：2018年5月27日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.remoting.hessian;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caucho.hessian.server.HessianServlet;

/**
 * UserHessianServlet
 * 
 * @author：youngzil@163.com
 * @2018年5月27日 上午9:36:03
 * @since 1.0
 */
@WebServlet(value = "/hessian/user", initParams = {@WebInitParam(name = "service-class", value = "org.quickstart.remoting.hessian.UserServiceImpl")})
public class UserHessianServlet extends HessianServlet {
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        super.init();
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
