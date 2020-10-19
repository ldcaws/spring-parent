package com.ldc.springboot_response.interceptor;

import com.ldc.springboot_response.annotation.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/19 20:14
 */
@Component
public class ResponseInterceptor extends HandlerInterceptorAdapter {
    public static final String RESPONSE_RESULT_ANNOTATION = "RESPONSE_RESULT_ANNOTATION";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求的方法
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            //判断是否在类对象或方法上面加了注解
            if (clazz.isAnnotationPresent(ResponseResult.class)) {//对象上面是否有注解
                //设置此请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANNOTATION,clazz.getAnnotation(ResponseResult.class));
            } else if (method.isAnnotationPresent(ResponseResult.class)) {//方法体上是否有注解
                //设置此请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANNOTATION,method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }



}
