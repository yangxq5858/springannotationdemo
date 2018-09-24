package com.hx.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author yxqiang
 * @create 2018-09-24 15:38
 */
public class WindowsCondition implements Condition {

    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //1.�ܻ�ȡ��bean��װ�乤��
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        //2.�ܻ�ȡ���������
        ClassLoader classLoader = context.getClassLoader();

        //3.�ܻ�ȡ����ǰ�Ļ�������
        Environment environment = context.getEnvironment();

        //4.�ܻ�ȡ��bean�����ע���ࣨ����ע����Ƴ�����ȣ�
        BeanDefinitionRegistry beanDefinitionRegistry = context.getRegistry();

        //�����ж�������bean��ע�������Ҳ���Ը�������ע��bean
        boolean beanDefinition = beanDefinitionRegistry.containsBeanDefinition("person");

        //��������ֻʹ�û�������
        String property = environment.getProperty("os.name");
        if (property.contains("Windows")){
            return true;
        }

        return false;
    }
}
