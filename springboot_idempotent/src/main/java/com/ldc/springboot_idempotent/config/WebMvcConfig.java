package com.ldc.springboot_idempotent.config;

import com.ldc.springboot_idempotent.interceptor.IdempotentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/13 11:35
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private IdempotentInterceptor idempotentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
    }
}
