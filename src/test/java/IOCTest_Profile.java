import com.hx.config.ProfileConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yxqiang
 * @create 2018-09-24 17:57
 */
public class IOCTest_Profile {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProfileConfig.class);

    //这里使用了vm的参数来指定 -Dspring.profiles.active=test
    @Test
    public void Test1(){
        PrintNames(applicationContext);
        applicationContext.close(); //关闭容器

    }

    //使用代码的方式，动态改变环境变量
    @Test
    public void Test2(){
        //1. 使用无参数的构造方法，创建IOC
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //2. 设置需要激活的环境 "test","dev"
        applicationContext.getEnvironment().setActiveProfiles("test","dev");
        //3. 注册配置类
        applicationContext.register(ProfileConfig.class);
        //4. 刷新容器
        applicationContext.refresh();

        PrintNames(applicationContext);

    }



    //打印容器中所有的bean
    private void PrintNames(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
