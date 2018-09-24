package com.hx.config;

import com.hx.bean.ColorFactoryBean;
import com.hx.bean.Person;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author yxqiang
 * @create 2018-09-23 21:08
 */

@Configuration
public class MainConfig {

    /**
     * 	   ConfigurableBeanFactory#SCOPE_PROTOTYPE prototype
     * 	   ConfigurableBeanFactory#SCOPE_SINGLETON singleton
     * 	   org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST request ��ʾͬһ������һ��
     * 	   org.springframework.web.context.WebApplicationContext#SCOPE_SESSION session ��ʾͬһ��Session����һ��
     */


    //@Scope(scopeName = "prototype")
    @Lazy
    @Bean(name = "person")//���¶���bean������Ϊperson
    public Person person1() {
        System.out.println("IOC�������� Person ������...");
        return new Person("lisi", 18);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
