import com.hx.config.BeanLifeCycleConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yxqiang
 * @create 2018-09-24 17:57
 */
public class IOCTest_LifeCycle {

    @Test
    public void Test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanLifeCycleConfig.class);
        System.out.println("�����������...");

        Object car = applicationContext.getBean("car");
        Object computer = applicationContext.getBean("computer");
        //��������
        applicationContext.close();
        System.out.println("�����Ѿ��ر�");



    }
}
