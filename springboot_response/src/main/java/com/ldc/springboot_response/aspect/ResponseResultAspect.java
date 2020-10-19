package com.ldc.springboot_response.aspect;

import com.ldc.springboot_response.response.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/19 21:42
 */
@Component
@Aspect
public class ResponseResultAspect {

    @Around("@annotation(com.ldc.springboot_response.annotation.ResponseResult)")
    public Object doSetFieldValue(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //前置增强
        System.out.println("前置增强操作");
        Object result = proceedingJoinPoint.proceed();
        //后置增强
        System.out.println("后置增强操作");
        return ResponseResult.success(result);
        //return result;
    }

}
