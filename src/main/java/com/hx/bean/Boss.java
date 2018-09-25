package com.hx.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yxqiang
 * @create 2018-09-25 21:21
 */
@Component
public class Boss {

//    @Autowired
    public Boss(Car car){ //标注在参数上
        this.car = car;
    }
    private Car car;
    public Car getCar() {
        return car;
    }
//    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }
    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
