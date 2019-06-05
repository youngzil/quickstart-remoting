/**
 * 项目名称：quickstart-netty 
 * 文件名：Person.java
 * 版本信息：
 * 日期：2017年1月17日
 * Copyright youngzil Corporation 2017
 * 版权所有 *
 */
package org.quickstart.remoting.netty.v4x.selfprotocol;

import java.io.Serializable;

/**
 * Person
 * 
 * @author：youngzil@163.com
 * @2017年1月17日 下午2:54:30
 * @version 1.0
 */
// 必须实现Serializable接口
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String sex;
    private int age;

    public String toString() {
        return "name:" + name + " sex:" + sex + " age:" + age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
