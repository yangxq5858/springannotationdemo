package com.hx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

/**
 * @author yxqiang
 * @create 2018-09-24 12:03
 */

//@ComponentScan //对@Controller @Repository @Service @Component 都会扫描出来
@ComponentScan(value = "com.hx",
//        excludeFilters = {
//        //表示过滤规则为：排除类型为注解类型的，类 = Controller的
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//},
        //表示 只包含的类型
        includeFilters = {
                //表示过滤规则为：类型为注解类型的，类 = Controller的
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class}),
                //表示过滤类型为 指定的类
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.hx.controller.BookController.class}),
                //按自定义规则 过滤
                @ComponentScan.Filter(type= FilterType.CUSTOM,classes = {MyFilterTypeImp.class})

        },
        useDefaultFilters = false //这个参数，默认为true，表示全部扫描，启用只包含时，要关闭此参数才生效
)
public class ComponentScanConfig {
}
