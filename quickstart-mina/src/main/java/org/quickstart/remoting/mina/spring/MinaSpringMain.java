package org.quickstart.remoting.mina.spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MinaSpringMain {

    public static void main(String[] args) throws Exception {
        if (System.getProperty("com.sun.management.jmxremote") != null) {
            System.out.println("JMX enabled.");
        } else {
            System.out.println("JMX disabled. Please set the " + "'com.sun.management.jmxremote' system property to enable JMX.");
        }
        getApplicationContext();
        System.out.println("Listening ...");
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
}
