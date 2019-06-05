/**
 * 项目名称：quickstart-remoting-hessian 
 * 文件名：UserServiceImpl.java
 * 版本信息：
 * 日期：2018年5月27日
 * Copyright youngzil Corporation 2018
 * 版权所有 *
 */
package org.quickstart.remoting.hessian;

import java.util.HashMap;
import java.util.Map;

/**
 * UserServiceImpl
 * 
 * @author：youngzil@163.com
 * @2018年5月27日 上午9:35:33
 * @since 1.0
 */
public class UserServiceImpl implements UserService {
    public String get() {
        return "get method invoke";
    }

    public Map<String, Object> getById(long id) {
        if (id <= 0) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", "英布");
        result.put("position", "九江王");
        return result;
    }
}
