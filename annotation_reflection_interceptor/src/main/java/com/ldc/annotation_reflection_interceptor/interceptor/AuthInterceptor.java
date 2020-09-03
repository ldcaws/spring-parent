package com.ldc.annotation_reflection_interceptor.interceptor;

import com.ldc.annotation_reflection_interceptor.annotation.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:权限校验拦截器
 * @author: ss
 * @time: 2020/9/3 14:51
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = findAuthCheck(handlerMethod);
        // 若没有添加权限注解则直接跳过，允许访问
        if (auth == null) {
            return true;
        }
        // 获取权限注解中的值
        boolean key = auth.key();
        // 演示，若key为true则校验通过，否则不通过
        if (!key) {
            System.out.println("校验未通过");
            throw new RuntimeException("校验未通过");
        }
        return true;
    }

    private Auth findAuthCheck(HandlerMethod handlerMethod) {
        // 在方法上的注解
        // 通过反射获取
        Auth annotation = handlerMethod.getMethodAnnotation(Auth.class);
        if (annotation == null) {
            // 在类上的注解
            // 通过反射获取
            annotation = handlerMethod.getBeanType().getAnnotation(Auth.class);
        }
        return annotation;
    }

}
