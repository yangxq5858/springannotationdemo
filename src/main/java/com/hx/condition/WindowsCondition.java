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
        //1.能获取到bean的装配工厂
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        //2.能获取到类加载器
        ClassLoader classLoader = context.getClassLoader();

        //3.能获取到当前的环境变量
        Environment environment = context.getEnvironment();

        //4.能获取到bean定义的注册类（可以注册和移除组件等）
        BeanDefinitionRegistry beanDefinitionRegistry = context.getRegistry();

        //可以判断容器中bean的注册情况，也可以给容器中注册bean
        boolean beanDefinition = beanDefinitionRegistry.containsBeanDefinition("person");

        //我们这里只使用环境变量
        String property = environment.getProperty("os.name");
        if (property.contains("Windows")){
            return true;
        }

        return false;
    }
}
