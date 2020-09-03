package com.ldc.annotation_reflection_aop.aspect;

import com.ldc.annotation_reflection_aop.util.BeanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @description:自定义业务封装的aop切面
 * @author: ss
 * @time: 2020/9/2 20:55
 */
@Component
@Aspect
public class SetFieldValueAspect {

    @Autowired
    private BeanUtil beanUtil;

    @Around("@annotation(com.ldc.annotation_reflection_aop.annotation.SetValue)")
    public Object doSetFieldValue(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //前置增强
        System.out.println("前置增强操作");
        Object result = proceedingJoinPoint.proceed();
        //List = [{id:1，customId:1,customName:null @SetValueField}]
        //向customName赋值》获取目标对象的结果对象
        //后置增强
        System.out.println("后置增强操作");
        beanUtil.setFieldValue((Collection) result);
        return result;
    }
}
