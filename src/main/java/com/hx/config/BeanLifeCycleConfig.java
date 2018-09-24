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
 * Bean ����������
 *   bean�Ĵ��� --> ��ʼ�� -->����
 *
 *   ���󴴽���
 *      ��ʵ������������ʼ��ʱ������
 *      ��ʵ�����ڵ�һ��ʹ��ʱ������
 *   �����ʼ����
 *      ���󴴽���ɺ󣬲���ֵ�󣬵��ó�ʼ������
 *   �������ϣ�
 *      ��ʵ���������ر�ʱ������
 *      ��ʵ�������������㣬��������
 *
 *   1) ͨ��@Bean�еĲ���ָ����ʼ�������ϵķ�����init destory��
 *   2) ͨ����bean���ʵ�ֳ�ʼ�������ϵĽӿ���ʵ��
 *      InitializingBean, DisposableBean ��2���ӿ�
 *   3) ͨ��jdk��JSR250 �Դ���ע��
 *      @PostConstruct ��bean������ɣ�����ֵ�󣬽���
 *      @PreDestory    ��bean����֮ǰ������֪ͨ������
 *   4) ͨ��ʵ��Spring�Ľӿ�BeanPostProcessor��2������
 *      postProcessBeforeInitialization: ��bean�����󣬳�ʼ��֮ǰ����
 *      postProcessAfterInitialization:  ��bean��ʼ��֮�����
 *
 *      ִ��˳����
 *      Car construct...
 *      postProcessBeforeInitialization car=>com.hx.bean.Car@15761df8
 *      Car init...
 *      postProcessAfterInitialization car=>com.hx.bean.Car@15761df8
 *   5) Spring����У�����ʹ����BeanPostProcessor
 *      ***���ǿ���ʹ��Spring���Ѿ�ʵ����BeanPostProcessor�ӿ�
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
