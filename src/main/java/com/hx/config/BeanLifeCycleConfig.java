package com.hx.config;

import com.hx.bean.Car;
import com.hx.bean.Computer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author yxqiang
 * @create 2018-09-24 17:31
 *
 * Bean 的生命周期
 *   bean的创建 --> 初始化 -->销废
 *
 *   对象创建：
 *      单实例：在容器初始化时，创建
 *      多实例：在第一次使用时，创建
 *   对象初始化：
 *      对象创建完成后，并赋值后，调用初始化方法
 *   对象销废：
 *      单实例：容器关闭时，销废
 *      多实例：容器不管你，即不销废
 *
 *   1) 通过@Bean中的参数指定初始化和销废的方法（init destory）
 *   2) 通过让bean组件实现Spring提供的初始化和销废的接口来实现
 *      InitializingBean, DisposableBean 这2个接口
 *   3) 通过jdk的JSR250 自带的注解
 *      @PostConstruct 在bean创建完成，并赋值后，进行
 *      @PreDestory    在bean销废之前，进行通知清理工作
 *   4) 通过实现Spring的接口BeanPostProcessor的2个方法
 *      postProcessBeforeInitialization: 在bean创建后，初始化之前调用
 *      postProcessAfterInitialization:  在bean初始化之后调用
 *
 *      执行顺序结果
 *      Car construct...
 *      postProcessBeforeInitialization car=>com.hx.bean.Car@15761df8
 *      Car init...
 *      postProcessAfterInitialization car=>com.hx.bean.Car@15761df8
 *   5) Spring框架中，大量使用了BeanPostProcessor
 *      ***我们可以使用Spring中已经实现了BeanPostProcessor接口
 *
 *
 */

@Configuration
@ComponentScan(value = "com.hx.bean")
public class BeanLifeCycleConfig {
    @Scope("prototype")
    @Bean(initMethod = "init",destroyMethod = "destory")
    public Car car(){
        return new Car();
    }

    @Bean
    public Computer computer(){
        return new Computer();
    }
}
