package com.hx.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author yxqiang
 * @create 2018-09-24 18:13
 */
public class Computer implements InitializingBean, DisposableBean {

    public Computer(){
        System.out.println("Computer constructor...");
    }


    public void afterPropertiesSet() throws Exception {
        System.out.println("Computer Init....");

    }

    public void destroy() throws Exception {
        System.out.println("Computer destory...");

    }
}
