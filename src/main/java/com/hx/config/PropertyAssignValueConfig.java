package com.hx.config;

import com.hx.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yxqiang
 * @create 2018-09-24 21:24
 *
 * �����Ը�ֵ������
 */

@Configuration
public class PropertyAssignValueConfig {

    @Bean
    public Person person(){
        return new Person();
    }
}
