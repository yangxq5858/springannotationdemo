package com.hx;

/**
 * @author yxqiang
 * @create 2018-09-23 21:10
 */
public class MainTest {

    public static void main(String[] args) {
        //这是以前xml配置文件的方式
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
//        Object person = applicationContext.getBean("person");
//        System.out.println(person);

        //这是采用注解配置的方式
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
//        Person person = (Person) annotationConfigApplicationContext.getBean("person1");
//        System.out.println(person);
//        String[] beanNamesForType = annotationConfigApplicationContext.getBeanNamesForType(Person.class);
//        for(String name:beanNamesForType){
//            System.out.println(name);
//        }


    }
}
