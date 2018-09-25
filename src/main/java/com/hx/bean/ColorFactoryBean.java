package com.hx.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author yxqiang
 * @create 2018-09-24 17:03
 * 创建一个Spring定义的工厂Bean
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    public Color getObject() throws Exception {
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
