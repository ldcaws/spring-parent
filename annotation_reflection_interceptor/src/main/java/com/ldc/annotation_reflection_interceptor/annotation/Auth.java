package com.ldc.annotation_reflection_interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义权限注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})//作用范围为类或方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    //是否需要验证，true为校验，false为不校验
    //自定义注解+反射+拦截器 实现权限校验
    boolean key() default true;
}
