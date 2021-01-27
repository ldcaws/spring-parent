package com.ldc.springboot_idempotent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等性方法注解
 * 此幂等性注解校验为无参，通过token（设置过期时间1秒）机制判断是否重复提交
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    String id();
    Class<?> lockClass();
}
