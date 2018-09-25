import com.hx.Repository.BookDao;
import com.hx.config.AutoWiredConfig;
import com.hx.config.PropertyAssignValueConfig;
import com.hx.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

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

    //打印容器中所有的bean
    private void PrintNames(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
