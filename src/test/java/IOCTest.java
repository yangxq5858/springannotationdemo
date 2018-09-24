import com.hx.bean.Person;
import com.hx.config.ComponentScanConfig;
import com.hx.config.ConditionalConfig;
import com.hx.config.ImportComponentConfig;
import com.hx.config.MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SystemEnvironmentPropertySource;

/**
 * @author yxqiang
 * @create 2018-09-23 20:52
 */
public class IOCTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

    //1 最基本的用法
    @Test
    public void test1() {

        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType) {
            System.out.println(name);
        }
    }

    //测试ComponentScan 包扫描
    @Test
    public void test2() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        PrintNames(applicationContext);
    }

    //测试类生成单例？
    @Test
    public void test3() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        System.out.println("IOC 容器创建完成了...");
        Object person1 = applicationContext.getBean("person");
        Object person2 = applicationContext.getBean("person");
        System.out.println(person1 == person2);

    }

    //测试条件注册组件
    @Test
    public void test4() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConditionalConfig.class);
        PrintNames(applicationContext);

    }

    private void PrintNames(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

    }

    //测试@ImportSelector 导入第三方包组件
    @Test
    public void test5() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportComponentConfig.class);
        PrintNames(applicationContext);
        Object red = applicationContext.getBean("Red");
        Object red1 = applicationContext.getBean("Red");
        System.out.println(red == red1);
    }

    //测试
    @Test
    public void test6(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        PrintNames(applicationContext);
        Object colorFactoryBean = applicationContext.getBean("colorFactoryBean");
        System.out.println("colorFactoryBean的类型="+colorFactoryBean.getClass());
        Object bean = applicationContext.getBean("&colorFactoryBean");
        System.out.println(bean.getClass());

    }

}
