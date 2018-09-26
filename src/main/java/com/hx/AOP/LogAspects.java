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
