package com.ldc.annotation_reflection_aop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ldc.annotation_reflection_aop.mapper")
public class AnnotationReflectionAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationReflectionAopApplication.class, args);
    }

}
