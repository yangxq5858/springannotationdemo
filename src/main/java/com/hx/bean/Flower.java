package com.hx.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author yxqiang
 * @create 2018-09-24 20:35
 */
@Component
public class Flower {

    public Flower(){
        System.out.println("Flower contructor...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Flower init...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("Flower destory");
    }
}
