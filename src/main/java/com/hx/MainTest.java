package com.hx;

import com.hx.bean.Person;
import com.hx.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yxqiang
 * @create 2018-09-23 21:10
 */
public class MainTest {

    public static void main(String[] args) {
        //������ǰxml�����ļ��ķ�ʽ
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
//        Object person = applicationContext.getBean("person");
//        System.out.println(person);

        //���ǲ���ע�����õķ�ʽ
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
//        Person person = (Person) annotationConfigApplicationContext.getBean("person1");
//        System.out.println(person);
//        String[] beanNamesForType = annotationConfigApplicationContext.getBeanNamesForType(Person.class);
//        for(String name:beanNamesForType){
//            System.out.println(name);
//        }


    }
}
