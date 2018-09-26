package com.hx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yangxinqiang
 * @content
 * @create 2018-09-26 15:57
 * 使用AOP 步骤
 * 1）导入Spring-aspects 包
 * 2）编写一个业务逻辑类(Mulcalcuator)
 * 3）定义一个日志切面类(LogAspects)
 *    通知方法：
 *       前置通知(@Before)：运行之前
 *       后置通知(@After): 运行结束之后（不管是正常结束还是异常结束）
 *       返回通知(@AfterReturning): 正常返回之后
 *       异常通知(@AfterThrowing): 出现异常之后
 *       环绕通知(@Around): 动态代理，手动推进目标方法运行（joinPoint.proceed)
 * 4) 给切面类的目标方法标注何时何地运行（通知注释）
 * 5）将切面类和目标类 都加入到容器中
 * 6) [关键步骤] 告诉Spring哪个类是切面类，即需要在LogAspects上标注@Aspect
 * 7) [关键步骤] 需要在配置类中，开启注解版的自动代理模式 @EnableAspectJAutoProxy
 *
 * 8）获取容器中的目标类的实例对象，执行
 *     使用目标类实例时，不要自己创建对象，否则无法使用到Spring的动态代理模式
 *
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan(value = "com.hx.AOP")
public class AopConfig {

}
