package com.ldc.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/4 21:43
 */
@Configuration
@EnableConfigurationProperties(TimeLogProperties.class)//加不加未影响结果
@Aspect
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "time.log",name = "enable",havingValue = "true",matchIfMissing = true)
public class TimeLogAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TimeLogAutoConfiguration.class);

    @Around("@annotation(com.ldc.logging.TimeLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String name = proceedingJoinPoint.getSignature().toLongString().split(" ")[2];
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("方法 {} 耗时 {} 毫秒",name,endTime-startTime);
        return result;
    }

}
