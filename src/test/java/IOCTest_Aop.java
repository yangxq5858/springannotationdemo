import com.hx.AOP.MulCalcuator;
import com.hx.config.AopConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yxqiang
 * @create 2018-09-24 17:57
 */
public class IOCTest_Aop {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);

    //这里使用了vm的参数来指定 -Dspring.profiles.active=test
    @Test
    public void Test1(){
        PrintNames(applicationContext);
        MulCalcuator mulCalcuator = applicationContext.getBean(MulCalcuator.class);
//        System.out.println(mulCalcuator);
        int div = mulCalcuator.div(9, 3);
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
