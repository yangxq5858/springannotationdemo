import com.hx.config.PropertyAssignValueConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author yxqiang
 * @create 2018-09-24 17:57
 */
public class IOCTest_PropertyValue {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertyAssignValueConfig.class);
    @Test
    public void Test1(){
        PrintNames(applicationContext);
        Object person = applicationContext.getBean("person");
        System.out.println(person);

        //获取IOC中的环境变量
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String nikeName = environment.getProperty("person.nikeName");
        System.out.println(nikeName);

        applicationContext.close(); //关闭容器

    }

    //打印容器中所有的bean
    private void PrintNames(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
