package com.ldc.annotation_reflection_aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetValueField {

    //@SetValueField(beanClass = UserMapper.class,param = "customId",method = "findUserById",targetField = "name")
    //method.invoke(bean,args)  通过传参的形式告诉

    //反射需要知道的类
    Class<?> beanClass();

    //反射执行所需的方法名
    String method();

    //反射方法的入参名
    String paramField();

    //指的是dao层的返回对象结果中的的所需字段名，可以通过该名字拿到具体的值
    String targetField();

}
