# SpringAnnotation Driven注解驱动笔记

## 1.容器组件注册（IOC）的3种方式

### 总结（重点是@Import和@Conditional2个用法，Spring boot大量使用）

#### a: 组件扫描 + 组件标注（@Controller @Service @Repository @Component)

自己写的组件，采用这种方式，因为可以修改源代码，在组件上面标注

#### b: @Bean

第三行包中的组件，我们没有办法自己修改源代码的情况，我们采用这种方式，在配置类中，new 一个对象出来

标注上@Bean，加入到容器中

#### c：***@Import

也是第三行的包中的组件，这种方式要快速些。

##### 1. @Import({Color.class,Blue.class}) 直接输入要导入的类

##### 2.@ImportSelector 自定义返回一个全路径类名的数组

注意，这里同样是采用@Import的方式导入的，只是导入的是一个Selector类

```java
@Configuration
@Import({Color.class, MyImportSelector.class})
public class ImportComponentConfig {
}
```

```java
public class MyImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{"com.hx.bean.Blue","com.hx.bean.Yellow"};
    }
}
```

#####  3.ImportBeanDefinitionRegistrar 这种方式可以自定义导入组件的名称

自定义规则

```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata 当前类的注解信息
     * @param registry bean定义的注册类
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        boolean blue = registry.containsBeanDefinition("com.hx.bean.Blue");
        boolean yellow = registry.containsBeanDefinition("com.hx.bean.Yellow");
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Red.class);
        rootBeanDefinition.setScope("prototype"); //设置组件的作用域，多例模式创建
        if (blue && yellow){
            registry.registerBeanDefinition("Red",rootBeanDefinition);
          //这里就可以对组件的名称进行修改了
        }
    }
}

```

导入

```java
@Configuration
@Import({Color.class, MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
public class ImportComponentConfig {
}

```

##### 4.BeanFactory<T>接口实现的类 结合@Bean 注册

这种方式是Spring整合第三方框架时，使用较多

**注意：工厂创建的类在注册进去后，我们在容器中拿到的类不是工厂类，而是工厂中getObject中产生的类**

```java
/**
 * @author yxqiang
 * @create 2018-09-24 17:03
 * 创建一个Spring定义的工厂Bean
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    public Color getObject() throws Exception {
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
```



在注册类中标注一个工厂bean

```
@Bean
public ColorFactoryBean colorFactoryBean(){
    return new ColorFactoryBean();
}
```

测试

```java
@Test
public void test6(){
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
    PrintNames(applicationContext);
    Object colorFactoryBean = applicationContext.getBean("colorFactoryBean");
    System.out.println("colorFactoryBean的类型="+colorFactoryBean.getClass());
}
```

输出

```java
colorFactoryBean
colorFactoryBean的类型=class com.hx.bean.Color

```

如果要拿到beanFactory 这个Bean，需要在bean 的id前加一个&符号

```java
public void test6(){
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
    PrintNames(applicationContext);
    Object colorFactoryBean = applicationContext.getBean("colorFactoryBean");
    System.out.println("colorFactoryBean的类型="+colorFactoryBean.getClass());
    Object bean = applicationContext.getBean("&colorFactoryBean"); //这个就是获取工厂bean本身的对象
    System.out.println(bean.getClass());

}
```

**Spring中的BeanFactory接口中，有一个默认的前缀 &**

```java
public interface BeanFactory {
    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String var1) throws BeansException;
```













### 1)采用配置类，代替xml配置的方式

```java

@Configuration //表示这是一个配置类
public class MainConfig {

    @Bean //给容器中注册一个bean，类型为返回值类型，id为返回值的名字
    public Person person(){
        return new Person("lisi",18); //注意，这里是手工new 了一个对象
    }
}

```

```java
public class Person {
    public String name;
    public Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
        super();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

**怎么使用**

```java
public class MainTest {

    public static void main(String[] args) {
        //这是以前xml配置文件的方式
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        applicationContext.getBean("person");
        //这是采用注解配置的方式
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = (Person) annotationConfigApplicationContext.getBean("person");
        System.out.println(person);
    }
}
```



### 2）包扫描的方式注入多个

```java
@ComponentScan(value="com.hx") //对@Controller @Repository @Service @Component 都会扫描出来
```

包扫描，还有多个参数，有包含和不包含包规则的用法

```java
//@ComponentScan //对@Controller @Repository @Service @Component 都会扫描出来
@ComponentScan(value = "com.hx",
//        excludeFilters = {
//        //表示过滤规则为：排除类型为注解类型的，类 = Controller的
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//},
        //表示 只包含的类型
        includeFilters = {
                //表示过滤规则为：类型为注解类型的，类 = Controller的
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class}),
                //表示过滤类型为 指定的类
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.hx.com.hx.controller.BookController.class}),
                //按自定义规则 过滤
                @ComponentScan.Filter(type= FilterType.CUSTOM,classes = {MyFilterTypeImp.class})

        },
        useDefaultFilters = false //这个参数，默认为true，表示全部扫描，启用只包含时，要关闭此参数才生效
)
public class ComponentScanConfig {
}

```

```java
@Repeatable(ComponentScans.class) //表示ComponentScan注解，可以重复即多次在同一个对象上使用
public @interface ComponentScan {
```

### 3）@Scope 创建对象的作用域

```
@Configuration
public class MainConfig {

    /**
     *        ConfigurableBeanFactory#SCOPE_PROTOTYPE prototype 多例 ioc启动时，不会创建对象，访问时，才会创建对象
     *        ConfigurableBeanFactory#SCOPE_SINGLETON singleton 单例 ioc启动时，默认是要创建对象，以后每次访问都是从 map.get() 一个对象
     *        org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST request 表示同一个请求一个
     *        org.springframework.web.context.WebApplicationContext#SCOPE_SESSION session 表示同一个Session生成一个
     */


    @Scope(scopeName = "prototype")
    @Bean(name = "person")//重新定义bean的名字为person
    public Person person1() {
        return new Person("lisi", 18);
    }
}
```

### 4）@Lazy 懒加载（针对单实例）

IOC 创建完成时，不会添加对象到容器中个，只有在第一次使用时，才会创建

```java
@Lazy
@Bean(name = "person")//重新定义bean的名字为person
public Person person1() {
    System.out.println("IOC容器创建 Person 对象了...");
    return new Person("lisi", 18);
}
```



### 5）***@Conditional 满足条件注册bean

SpringBoot中大量使用这种方式

Ctrl + shift+ T 打开一个类型（Open Type），输入Conditional。

```java
public interface Condition {

   /**
    * Determine if the condition matches.
    * @param context the condition context
    * @param metadata metadata of the {@link org.springframework.core.type.AnnotationMetadata class}
    * or {@link org.springframework.core.type.MethodMetadata method} being checked.
    * @return {@code true} if the condition matches and the component can be registered
    * or {@code false} to veto registration.
    */
   boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);

}

```

定义两个条件类

```java
public class WindowsCondition implements Condition {

    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //1.能获取到bean的装配工厂
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        //2.能获取到类加载器
        ClassLoader classLoader = context.getClassLoader();

        //3.能获取到当前的环境变量
        Environment environment = context.getEnvironment();

        //4.能获取到bean定义的注册类（可以注册和移除组件等）
        BeanDefinitionRegistry beanDefinitionRegistry = context.getRegistry();

        //我们这里只使用环境变量
        String property = environment.getProperty("os.name");
        if (property.contains("Windows")){
            return true;
        }

        return false;
    }
}
```

```java
 /**
     *
     * ConditionContext 判断条件能使用的上下文环境
     * AnnotatedTypeMetadata 注释信息
     * @return
     */
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        //1.能获取到bean的装配工厂
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        //2.能获取到类加载器
        ClassLoader classLoader = context.getClassLoader();

        //3.能获取到当前的环境变量
        Environment environment = context.getEnvironment();

        //4.能获取到bean定义的注册类（可以注册和移除组件等）
        BeanDefinitionRegistry beanDefinitionRegistry = context.getRegistry();

        //我们这里只使用环境变量
        String property = environment.getProperty("os.name");
        if (property.contains("Linux")){
            return true;
        }

        return false;
    }
}
```

在配置类上标上注解

```java
/**
 * @author yxqiang
 * @create 2018-09-24 15:34
 *
 * 使用Conditional条件注册组件
 *
 * 当操作系统为windows时，注册person001，当操作系统各位linux时，注册person002组件
 */
@Conditional(WindowsCondition.class) //条件注册也可以放到类上面，表示满足条件时，下面的对象才会被注册进容器中
@Configuration
public class ConditionalConfig {

//    @Conditional(WindowsCondition.class)
    @Bean("person-windows")
    public Person person001(){
        return new Person("Windows Pserson",20);
    }

//    @Conditional(LinuxCondition.class)
    @Bean("person-linux")
    public Person person002(){
        return new Person("Linux Pserson",20);
    }
}

```



### 6）@Import(Color.class) 导入第三行包

@Bean 也是可以导入第三方包的

```java
@Configuration
@Import(Color.class)
public class ImportComponentConfig {
}
```

IOC容器中得到的名字为：com.hx.bean.Color **全路径类名，详细的见 笔记的顶部上的总结**

## 2.Bean的生命周期

```
Bean 的生命周期
   bean的创建 --> 初始化 -->销废
 对象创建：
 *      单实例：在容器初始化时，创建
 *      多实例：在第一次使用时，创建
 *   对象初始化：
 *      对象创建完成后，并赋值后，调用初始化方法
 *   对象销废：
 *      单实例：容器关闭时，销废
 *      多实例：容器不管你，即不销废
 
 
```

4种方式，可以实现对Bean生命周期进行管理

### 1) 通过@Bean中的参数指定初始化和销废的方法（init destory）

```java
public class Car {
    public Car(){
        System.out.println("Car construct...");
    }
    public void init(){
        System.out.println("Car init...");
    }
    public void destory(){
        System.out.println("Car destory...");
    }
}
```

```java
@Bean(initMethod = "init",destroyMethod = "destory")
public Car car(){
    return new Car();
}
```

### 2)通过让bean组件实现初始化和销废的接口来实现*      InitializingBean, DisposableBean 这2个接口

```java
public class Computer implements InitializingBean, DisposableBean {

    public Computer(){
        System.out.println("Computer constructor...");
    }


    public void afterPropertiesSet() throws Exception {
        System.out.println("Computer Init....");

    }

    public void destroy() throws Exception {
        System.out.println("Computer destory...");

    }
}
```

### 3)通过jdk的JSR250 自带的注解

```
@PostConstruct 在bean创建完成，并赋值后，进行
@PreDestory    在bean销废之前，进行通知清理工作
```

```java
public class Flower {

    public Flower(){
        System.out.println("Flower contructor...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Flower init...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("Flower destory");
    }
}
```

### 4)实现Spring的BeanPostProcessor【Interface】的2个方法

```java
postProcessBeforeInitialization: 在bean创建后，初始化之前调用
postProcessAfterInitialization:  在bean初始化之后调用
```



执行顺序

```
  执行顺序结果
*      Car construct...
*      postProcessBeforeInitialization car=>com.hx.bean.Car@15761df8
*      Car init...
*      postProcessAfterInitialization car=>com.hx.bean.Car@15761df8
```

### 5)Spring 底层框架大量使用 BeanPostProcessor 后置处理器

不管是bean的赋值，注入其他组件，@Autowired，生命周期注解功能等等，都是用的BeanPostProcessor来完成的

```java
BeanPostProcessor (org.springframework.beans.factory.config)
//以下都是BeanPostProcessor的实现类
MyPostProcessor (com.hx.bean) //自己写的实现类

AdvisorAdapterRegistrationManager (org.springframework.aop.framework.adapter)

BeanPostProcessorChecker in PostProcessorRegistrationDelegate (org.springframework.context.support)

LoadTimeWeaverAwareProcessor (org.springframework.context.weaving)

AbstractAdvisingBeanPostProcessor (org.springframework.aop.framework)

DestructionAwareBeanPostProcessor (org.springframework.beans.factory.config)
//
ApplicationContextAwareProcessor (org.springframework.context.support)

MergedBeanDefinitionPostProcessor (org.springframework.beans.factory.support)

BeanValidationPostProcessor (org.springframework.validation.beanvalidation)

InstantiationAwareBeanPostProcessor (org.springframework.beans.factory.config)

```

通过ApplicationContextAware接口，可以得到IOC容器

```java
public class Dog implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Dog(){
        System.out.println("Dog contructor...");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

```

为什么实现ApplicationContextAware接口，可以得到IOC容器

```java
class ApplicationContextAwareProcessor implements BeanPostProcessor {

@Override
public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
		AccessControlContext acc = null;

		if (System.getSecurityManager() != null &&
				(bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware ||
						bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware ||
						bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware //1. 判断bean是否是实现了ApplicationContextAware接口)) {
			acc = this.applicationContext.getBeanFactory().getAccessControlContext();
		}

		if (acc != null) {
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				@Override
				public Object run() {
					invokeAwareInterfaces(bean);
					return null;
				}
			}, acc);
		}
		else {
			invokeAwareInterfaces(bean); //acc不为空时，调用invokeAwareInterfaces
		}

		return bean;
	}

}
```

```java
private void invokeAwareInterfaces(Object bean) {
   if (bean instanceof Aware) {
      if (bean instanceof EnvironmentAware) {
         ((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
      }
      if (bean instanceof EmbeddedValueResolverAware) {
         ((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(this.embeddedValueResolver);
      }
      if (bean instanceof ResourceLoaderAware) {
         ((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
      }
      if (bean instanceof ApplicationEventPublisherAware) {
         ((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
      }
      if (bean instanceof MessageSourceAware) {
         ((MessageSourceAware) bean).setMessageSource(this.applicationContext);
      }
     //当实现了ApplicationContextAware接口时，要调用setApplicationContext(this.applicationContext)，将ioc容器传递给bean，这样bean就可以得到IOC容器了
      if (bean instanceof ApplicationContextAware) {
         ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
      }
   }
}
```

## 3.Bean赋值

### 1)使用@Value赋值

```
public class Person {

    //使用@Value赋值
    //1. 基本类型的值
    //2. 可以用SpEL表达式 #{20-2}
    //3. 可以使用环境变量中的值 ${}
    @Value("yxqiang888")
    public String name;
    @Value("#{20-2}")
    public Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
        super();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

### 2)PropertySource读取外部配置文件

```
//使用PropertySource读取外部配置文件的key/value 保存到运行的环境变量中，Bean的属性上就可以使用${key}获取值
@PropertySource(value="classpath:/person.properties")
```

环境变量是指Spring IOC中的environment

```java
//获取IOC中的环境变量
ConfigurableEnvironment environment = applicationContext.getEnvironment();
String nikeName = environment.getProperty("person.nikeName");
System.out.println(nikeName); //同样可以得到值	
```



Bean上的属性，就可以采用表达式获取值了

```java
public class Person {

    //使用@Value赋值
    //1. 基本类型的值
    //2. 可以用SpEL表达式 #{20-2}
    //3. 可以使用环境变量中的值 ${}
    @Value("yxqiang888")
    private String name;
    @Value("#{20-2}")
    private Integer age;

    @Value("${person.nikeName}") //这里就是读取的配置文件保存在环境变量中的值
    private String nickName;

```

**综合例子（可以在Bean上，参数上使用@Value，还可以用Spring的解析器EmbeddedValueResolverAware）：**

```java
@Configuration
@PropertySource(value = "classpath:/dbconfig.properties")
public class ProfileConfig implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    //引入Spring的字符串解析器
    private StringValueResolver resolver;
    private String driverClass;



    @Profile("test")
    @Bean
    public Yellow yellow(){
        return new Yellow();
    };

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test1");
        return dataSource;

    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test2");
        return dataSource;

    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test3");
        return dataSource;

    }


    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.resolver = stringValueResolver;
        driverClass = resolver.resolveStringValue("${db.driverClass}");
    }
}
```



### 3) 自动装配

Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值, **AutowiredAnnotationBeanPostProcessor** 来实现自动注入的

以下为自动装配的3种方式，推荐使用Spring提供的@Autowired。

####  1. @AutoWired

```java
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
}
```

```java
@Service
public class BookService {
  /**
   //AutoWired装配规则
   1）默认优先按照类型去容器中查找，找到就装配，如：
     BookDao bean = applicationContext.getBean(BookDao.class);
   2）如果查找到多个组件，再将属性的名称作为id去IOC容器中查找
     BookDao bean = applicationContext.getBean(BookDao.class);
   3）使用 @Qualifier("bookDao") 注解，明确指定是哪一个Bean，用于自动装配进来
   4) 使用 @Primary在Bean上标注，表示多个实例时，优先装配的Bean；前提是没有使用@Qualifier
   5）@Autowired(required = false) //required = false 表示能装配上，就装配，否则为空时，要报错
 */    
     
     
    @Autowired
    private BookDao bookDao;
}
```

```java
public class AutoWiredConfig {
    @Primary //指明当前的Bean为默认首选的Bean
    @Bean("bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }
}
```

#### 2.@Resource

JSR250规范

@Resource(name="bookDao2")：

   可以和@Autowired一样实现自动装配，默认也是按照属性名称来装配组件的，也可以指定名称

   没有能支持@Primary 和 @Qulifier,以及@Autowired(required=false)的特性。

#### 3.@Inject

JSR330规范

@Inject：

​    需要在POM中单独导入 javax.inject的包，和Autowired功能基本一样，没有 @Primary支持







。。。。。整合家里的





## 4.@Profile 多环境注解

```
 指定组件在哪个环境时，才注册到容器中。
*   1）只有在被激活时，组件才会被注册进去，@Profile("default") 表示默认，和没有标注profile效果是一样的
*   2) @profile可以设置在类上,也可以直接在方法上
```

```java
@Profile("test")
@Configuration
@PropertySource(value = "classpath:/dbconfig.properties")
public class ProfileConfig implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    //引入Spring的字符串解析器
    private StringValueResolver resolver;
    private String driverClass;



    @Profile("test")
    @Bean
    public Yellow yellow(){
        return new Yellow();
    };

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test1");
        return dataSource;

    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test2");
        return dataSource;

    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String pwd ) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test3");
        return dataSource;

    }


    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.resolver = stringValueResolver;
        driverClass = resolver.resolveStringValue("${db.driverClass}");
    }
}
```



怎么使用？

```java
*   1）在vm命令参数加上 -Dspring.profiles.active=test
*   2) 使用代码方式
*         //1. 使用无参数的构造方法，创建IOC
*         AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
*         //2. 设置需要激活的环境 "test","dev"
*         applicationContext.getEnvironment().setActiveProfiles("test","dev");
*         //3. 注册配置类
*         applicationContext.register(ProfileConfig.class);
*         //4. 刷新容器
*         applicationContext.refresh();
```

```java
import com.hx.config.AutoWiredConfig;
import com.hx.config.ProfileConfig;
import com.hx.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;

import javax.swing.plaf.synth.Region;

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

```



## 5) AOP(动态代理实现方式)

AOP：指在程序运行期间动态的将某段代码切入到指定方法 指定位置进行运行的编程方式；

```java
* 1）导入Spring-aspects 包
* 2）编写一个业务逻辑类(Mulcalcuator)
* 3）定义一个日志切面类(LogAspects)
*    通知方法：
*       前置通知(@Before)：运行之前
*       后置通知(@After): 运行结束之后（不管是正常结束还是异常结束）
*       返回通知(@AfterReturning): 正常返回之后
*       异常通知(@AfterThrowing): 出现异常之后
*       环绕通知(@Around): 动态代理，手动推进目标方法运行（joinPoint.proceed)

* 4）将切面类和目标类 都加入到容器中
* 5) [关键步骤] 告诉Spring哪个类是切面类，即需要在LogAspects上标注@Aspect
* 6) [关键步骤] 需要在配置类中，开启注解版的自动代理模式 @EnableAspectJAutoProxy
*
* 7）获取容器中的目标类的实例对象，执行
*     使用目标类实例时，不要自己创建对象，否则无法使用到Spring的动态代理模式
```

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>4.3.12.RELEASE</version>
</dependency>
```

业务逻辑类

```
@Component
public class MulCalcuator {
    public int div(int i,int j){
        return i / j;
    }
}
```

切面类

```java
package com.hx.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author yangxinqiang
 * @content
 * @create 2018-09-26 16:02
 */
@Aspect //指定当前类为切面类 ******
@Component
public class LogAspects {

    //如果不想每个注解上都写同一个表达式，可以抽取公共的切入点表达式
    //1. 本类内使用，就不用加上包名，其他类如果要用的话，需要加上全路径
    @Pointcut("execution(public int com.hx.AOP.MulCalcuator.*(..))")
    public void pointCut(){};


    //如果不想public int com.hx.AOP.MulCalcuator.*(..)  第一个* 表示所有的方法，括号后的两个.. 表示，所有的参数
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String methedName = joinPoint.getTarget().getClass().getName();
        List<Object> argList = Arrays.asList(args);
        System.out.println(methedName+"开始执行...参数列表："+argList);
    }

    @After("com.hx.AOP.LogAspects.pointCut()")
    public void logEnd(){
        System.out.println("方法执行完毕...");
    }

    //Object result：表示用于接收返回值的对象
    //注意这里 joinPoint 参数必须放在第一个，否则Spring无法识别
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println("方法返回值...运行结果：{"+result+"}");
    }

    //Exception exception：表示用于接收异常的信息
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(Exception exception){
        System.out.println("方法执行异常...异常信息:{"+exception+"}");
    }
}

```



切面配置类

```java
package com.hx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy  //这句话就是开启自动代理
@Configuration
@ComponentScan(value = "com.hx.AOP")
public class AopConfig {

}

```

测试

```java
import com.hx.AOP.MulCalcuator;
import com.hx.config.AopConfig;
import com.hx.config.ProfileConfig;
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

```

























































