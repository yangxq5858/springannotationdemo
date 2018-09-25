package com.hx.config;

import com.hx.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yxqiang
 * @create 2018-09-24 21:24
 *
 * 给属性赋值的配置
 */

//使用PropertySource读取外部配置文件的key/value 保存到运行的环境变量中，Bean的属性上就可以使用${key}获取值
@PropertySource(value="classpath:/person.properties")
@Configuration

public class PropertyAssignValueConfig {

    @Bean
    public Person person(){
        return new Person();
    }
}
