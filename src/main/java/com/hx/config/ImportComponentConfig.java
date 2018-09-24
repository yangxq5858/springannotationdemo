package com.hx.config;

import com.hx.bean.Color;
import com.hx.condition.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yxqiang
 * @create 2018-09-24 16:18
 */

@Configuration
@Import({Color.class, MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
public class ImportComponentConfig {
}
