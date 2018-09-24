package com.hx.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author yxqiang
 * @create 2018-09-23 21:08
 */


public class Person {

    //使用@Value赋值
    //1. 基本类型的值
    //2. 可以用SpEL表达式 #{20-2}
    //3. 可以使用环境变量中的值 ${}
    @Value("yxqiang888")
    public String name;
    @Value("#{20-2}")
    public Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
        super();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
