package com.hx.config;

import com.hx.bean.Car;
import com.hx.Repository.BookDao;
import com.hx.bean.Color;
import org.springframework.context.annotation.*;

/**
 * @author yangxinqiang
 * @create 2018-09-25 10:45
 * 自动装配的配置类
 *
 *    //AutoWired装配规则
 *    1）默认优先按照类型去容器中查找，找到就装配，如：
 *     BookDao bean = applicationContext.getBean(BookDao.class);
 *    2）如果查找到多个组件，再将属性的名称作为id去IOC容器中查找
 *     BookDao bean = applicationContext.getBean(BookDao.class);
 *    3）使用 @Qualifier("bookDao") 注解，明确指定是哪一个Bean，用于自动装配进来
 *    4）@Autowired(required = false) //required = false 表示能装配上，就装配，否则为空时，要报错
 *    5）@Primary，不用@Qualifier，在Bean上设置，表示查找到多对象时，指定为优先级最高的Bean
 */

@Configuration
@ComponentScan({"com.hx.controller","com.hx.service","com.hx.Repository","com.hx.bean"})
//@Import(value = {BookController.class, BookService.class, BookDao.class})
public class AutoWiredConfig {


    @Primary //指明当前的Bean为默认首选的Bean
    @Bean("bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }

    @Bean
    public Color color(Car car){ //这里使用了参数的自动装配，@Autowired 是省略了的
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}
