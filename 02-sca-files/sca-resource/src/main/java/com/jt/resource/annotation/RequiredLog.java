package com.jt.resource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解:
 * 通过此注解描述需要执行扩展业务逻辑的方法,
 * 简单点就是做个标识.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredLog {
    String value() default "";
    //...........

}
