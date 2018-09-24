package com.hx.config;

import com.hx.bean.Red;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yxqiang
 * @create 2018-09-24 16:41
 */


public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata 当前类的注解信息
     * @param registry bean定义的注册类
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        boolean blue = registry.containsBeanDefinition("com.hx.bean.Blue");
        boolean yellow = registry.containsBeanDefinition("com.hx.bean.Yellow");
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Red.class);
        rootBeanDefinition.setScope("prototype");
        if (blue && yellow){
            registry.registerBeanDefinition("Red",rootBeanDefinition);
        }
    }
}
