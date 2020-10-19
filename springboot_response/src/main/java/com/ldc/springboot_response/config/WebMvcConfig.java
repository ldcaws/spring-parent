package com.ldc.springboot_response.config;

import com.ldc.springboot_response.interceptor.ResponseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:拦截器配置类
 * @author: ss
 * @time: 2020/10/19 20:13
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ResponseInterceptor responseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseInterceptor).addPathPatterns("/**");
    }
}