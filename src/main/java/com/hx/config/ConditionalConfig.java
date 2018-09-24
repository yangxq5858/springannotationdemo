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
 * ʹ��Conditional����ע�����
 *
 * ������ϵͳΪwindowsʱ��ע��person001��������ϵͳ��λlinuxʱ��ע��person002���
 */


@Conditional(WindowsCondition.class) //����ע��Ҳ���Էŵ������棬��ʾ��������ʱ������Ķ���Żᱻע���������
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
