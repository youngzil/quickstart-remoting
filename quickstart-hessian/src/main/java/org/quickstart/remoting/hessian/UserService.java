/**
 * 项目名称：quickstart-remoting-hessian 
 * 文件名：UserService.java
 * 版本信息：
 * 日期：2018年5月27日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.remoting.hessian;

import java.util.Map;

/**
 * UserService
 * 
 * @author：youngzil@163.com
 * @2018年5月27日 上午9:35:12
 * @since 1.0
 */
public interface UserService {
    public Map<String, Object> getById(long id);

    public String get();
}
