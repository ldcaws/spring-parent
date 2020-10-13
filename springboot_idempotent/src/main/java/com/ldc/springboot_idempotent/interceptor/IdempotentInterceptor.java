package com.ldc.springboot_idempotent.interceptor;

import com.ldc.springboot_idempotent.annotation.Idempotent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/13 11:32
 */
@Component
public class IdempotentInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取方法上的注解
        Idempotent annotation = handlerMethod.getMethodAnnotation(Idempotent.class);
        if (annotation != null) {
            //幂等性校验
            System.out.println("幂等性校验");
            String token = request.getHeader("Authorization");
            if (StringUtils.isEmpty(token)) {
                System.out.println("token为空");
                return false;
            }
        }
        return true;
    }
}
