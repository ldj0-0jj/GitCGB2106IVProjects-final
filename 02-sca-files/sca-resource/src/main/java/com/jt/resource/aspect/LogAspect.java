package com.jt.resource.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 定义一个AOP设置中的切面对象.
 * 思考:切面对象的构成?
 * 1)切入点(执行拓展业务逻辑的入口):@Pointcut
 * 2)通知方法(封装拓展业务逻辑):@Around,....
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    //@Pointcut中定义切入点表达式
    //表达式中要描述在哪些地方定义切入点.
    //表达式的定义有多种写法,在这里我采用注解方式的表达式
    @Pointcut("@annotation(com.jt.resource.annotation.RequiredLog)")
    public void doLog(){
        //这里不用写任何代码,这个方法只是为承载 @Pointcut注解
    }
    /**
     * 在执行的切入点方法上执行@Around注解描述的方法
     * @param jp 连接点(封装了你要执行的执行链信息,包括目标方法信息)
     * @return 目标方法的返回值
     * @throws Throwable
     */
    @Around("doLog()")
    //@Around("@annotation(com.jt.resource.annotation.RequiredLog)")
    public Object doAround(ProceedingJoinPoint jp)throws Throwable{
        log.info("Around.Before {}",System.currentTimeMillis());
        Object result=jp.proceed();//执行我们的目标执行链(包含切面,目标方法)
        log.info("Around.After {}", System.currentTimeMillis());
        //后续思考如何将日志写到数据库?
        return result;
    }

}
