package com.ldc.springboot_idempotent.aop;

import com.ldc.springboot_idempotent.common.IdempotentUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/13 11:05
 */
@Component
@Aspect
public class IdempotentAspect {

    @Around("@annotation(com.ldc.springboot_idempotent.annotation.Idempotent)")
    public Object idempotent(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        //前置增强
        System.out.println("前置增强操作");
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            System.out.println("token为空");
            return "token为空";
        }
        //IdempotentUtil.check();
        Object rs = pjp.proceed();
        //后置增强
        System.out.println("后置增强操作");
        return rs;
        //此aop切面处理有问题，改用拦截器处理
    }
}
