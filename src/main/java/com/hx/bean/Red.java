package com.hx.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author yxqiang
 * @create 2018-09-24 16:43
 */
@Component
public class Red implements EmbeddedValueResolverAware, ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String stringValue = resolver.resolveStringValue("我的系统是 ${os.name} SpEL表达式值=#{20*5}");
        System.out.println(stringValue);


    }
}
