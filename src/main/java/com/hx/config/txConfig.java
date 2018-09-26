package com.hx.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author yxqiang
 * @create 2018-09-26 20:05
 *
 * 环境准备：
 *   1）准备数据源c3p0, 数据库驱动 mysql，导入 Spring-jdbc
 *   2）配置数据源 JdbcTemplate(Spring 提供的简化的数据库操作的工具类）操作数据
 *   3）给方法上加上@Transcation
 *   4) 给配置类加上EnableTransactionManagement 开启事务管理功能
 *   5）配置事务管理器，即注册事务管理器在容器中
 */

@EnableTransactionManagement
@Configuration
public class txConfig {

    //数据源
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test2");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds) throws PropertyVetoException { //这里的参数，就会自动注入
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource()); //也可以这样写，不会多创建一个Bean，Spring 会对Configuration类特殊处理
        return jdbcTemplate;
    }

    //注册事务管理器在容器中
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
        return dataSourceTransactionManager;
    }
}
