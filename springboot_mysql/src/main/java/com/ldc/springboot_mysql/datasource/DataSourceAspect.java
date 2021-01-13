package com.ldc.springboot_mysql.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description: 设置数据源切面
 * @author: ldc
 * @time: 2021/1/13 21:44
 */
@Aspect
@Order(value = 1)
@Component
public class DataSourceAspect {

    @Around("@annotation(com.ldc.springboot_mysql.datasource.DataSourceAnnotation)")
    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
        boolean clear = false;
        try {
            Method method = this.getMethod(pjp);
            DataSourceAnnotation dataSourceAnnotation = method.getAnnotation(DataSourceAnnotation.class);
            clear = dataSourceAnnotation.clear();
            DataSourceContextHolder.set(dataSourceAnnotation.value().getName());
            System.out.println(String.format("数据源切换至：{%s}", dataSourceAnnotation.value().getName()));
            return pjp.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.clear();
            }
        }
    }

    private Method getMethod(JoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

}

