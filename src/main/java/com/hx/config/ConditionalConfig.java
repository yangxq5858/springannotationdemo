package com.hx.config;

import com.hx.bean.Person;
import com.hx.condition.LinuxCondition;
import com.hx.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author yxqiang
 * @create 2018-09-24 15:34
 *
 * 使用Conditional条件注册组件
 *
 * 当操作系统为windows时，注册person001，当操作系统各位linux时，注册person002组件
 */


@Conditional(WindowsCondition.class) //条件注册也可以放到类上面，表示满足条件时，下面的对象才会被注册进容器中
@Configuration
public class ConditionalConfig {

//    @Conditional(WindowsCondition.class)
    @Bean("person-windows")
    public Person person001(){
        return new Person("Windows Pserson",20);
    }

//    @Conditional(LinuxCondition.class)
    @Bean("person-linux")
    public Person person002(){
        return new Person("Linux Pserson",20);
    }


}
