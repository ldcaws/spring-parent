package com.ldc.springboot_response.interceptor;

import com.ldc.springboot_response.annotation.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/19 21:20
 */
@ControllerAdvice
//@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    //标记名称
    public static final String RESPONSE_RESULT_ANNOTATION = "RESPONSE_RESULT_ANNOTATION";

    //是否请求了包含了包装注解标记，没有则直接返回，不需要重写返回体
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        //判断请求是否有包装标记
        ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANNOTATION);
        return responseResult == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("进入返回体，包装处理中...");
        return com.ldc.springboot_response.response.ResponseResult.success(body);
    }
}
