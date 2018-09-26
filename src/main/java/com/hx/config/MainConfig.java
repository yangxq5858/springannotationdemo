package com.hx.config;

import com.hx.bean.ColorFactoryBean;
import com.hx.bean.Person;
import org.springframework.context.annotation.*;

/**
 * @author yxqiang
 * @create 2018-09-23 21:08
 */

@Configuration
public class MainConfig {

    /**
     * 	   ConfigurableBeanFactory#SCOPE_PROTOTYPE prototype
     * 	   ConfigurableBeanFactory#SCOPE_SINGLETON singleton
     * 	   org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST request 表示同一个请求一个
     * 	   org.springframework.web.context.WebApplicationContext#SCOPE_SESSION session 表示同一个Session生成一个
     */


    //@Scope(scopeName = "prototype")
    @Lazy
    @Bean(name = "person")//重新定义bean的名字为person
    public Person person1() {
        System.out.println("IOC容器创建 Person 对象了...");
        return new Person("lisi", 18);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
