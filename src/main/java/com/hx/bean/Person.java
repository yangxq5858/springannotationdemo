package com.hx.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author yxqiang
 * @create 2018-09-23 21:08
 */


public class Person {

    //ʹ��@Value��ֵ
    //1. �������͵�ֵ
    //2. ������SpEL���ʽ #{20-2}
    //3. ����ʹ�û��������е�ֵ ${}
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
