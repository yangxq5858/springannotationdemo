package com.hx.config;

<<<<<<< HEAD
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
=======
import com.hx.bean.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;
>>>>>>> origin/master

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
<<<<<<< HEAD
 * @author yxqiang
 * @create 2018-09-25 22:23
 */

@Configuration
public class ProfileConfig {

//    @Value("${}")
//    private String user;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.driver");
        return dataSource;
    }

=======
 * @author yangxinqiang
 * @content
 * @create 2018-09-26 11:33
 *
 * 这里采用了3种方式来读取外部文件的值（其实都是获取的环境变量里的值）
 * 1）直接在属性上标注 @Value("${db.user}")
 * 2）在Bean的构造器的方法参数中使用 @Value("${db.password}") 注解
 * 3）实现一个Spring的Aware接口，得到字符串解析器，用解析器来解析
 *
 *
 * @profile:
 *   指定组件在哪个环境时，才注册到容器中。
 *   1）只有在被激活时，组件才会被注册进去，@Profile("default") 表示默认，和没有标注profile效果是一样的
 *   2) @profile可以设置在类上,也可以直接在方法上
 *
 * 使用方式：
 *   1）在vm命令参数加上 -Dspring.profiles.active=test
 *   2) 使用代码方式
 *         //1. 使用无参数的构造方法，创建IOC
 *         AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
 *         //2. 设置需要激活的环境 "test","dev"
 *         applicationContext.getEnvironment().setActiveProfiles("test","dev");
 *         //3. 注册配置类
 *         applicationContext.register(ProfileConfig.class);
 *         //4. 刷新容器
 *         applicationContext.refresh();
 *
 *
 */

@Profile("test")
@Configuration
@PropertySource(value = "classpath:/dbconfig.properties")
public class ProfileConfig implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    //引入Spring的字符串解析器
    private StringValueResolver resolver;
    private String driverClass;



    @Profile("test")
    @Bean
    public Yellow yellow(){
        return new Yellow();
    };

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test1");
        return dataSource;

    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test2");
        return dataSource;

    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test3");
        return dataSource;

    }


    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.resolver = stringValueResolver;
        driverClass = resolver.resolveStringValue("${db.driverClass}");
    }
>>>>>>> origin/master
}
