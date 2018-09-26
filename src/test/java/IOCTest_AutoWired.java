import com.hx.bean.Boss;
import com.hx.bean.Car;
import com.hx.bean.Color;
import com.hx.config.AutoWiredConfig;
import com.hx.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yxqiang
 * @create 2018-09-24 17:57
 */
public class IOCTest_AutoWired {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoWiredConfig.class);
    @Test
    public void Test1(){
        PrintNames(applicationContext);
        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);

        applicationContext.close(); //关闭容器

    }

    @Test
    public void Test2(){
        Boss boss = applicationContext.getBean(Boss.class);
        Car car = applicationContext.getBean(Car.class);
        Color color = applicationContext.getBean(Color.class);
        System.out.println(car);
        System.out.println(boss);
        System.out.println(color);

    }


    //打印容器中所有的bean
    private void PrintNames(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }


}
