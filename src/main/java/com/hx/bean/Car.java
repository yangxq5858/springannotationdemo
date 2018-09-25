package com.hx.bean;

import org.springframework.stereotype.Component;

/**
 * @author yxqiang
 * @create 2018-09-24 17:50
 */

@Component
public class Car {


    public Car(){
        System.out.println("Car constructor...");
    }

    public void init(){
        System.out.println("Car init...");
    }
    public void destory(){
        System.out.println("Car destory...");
    }
}
