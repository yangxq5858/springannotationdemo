package com.hx.bean;

/**
 * @author yxqiang
 * @create 2018-09-24 16:17
 */
public class Color {

    private  Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }
}
