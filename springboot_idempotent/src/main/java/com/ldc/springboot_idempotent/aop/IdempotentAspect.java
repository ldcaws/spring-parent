package com.ldc.springboot_idempotent.aop;

import com.ldc.springboot_idempotent.annotation.Idempotent;
import com.ldc.springboot_idempotent.common.IdempotentUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取参数
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = pjp.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        String id = annotation.id();
        Class<?> lockClass = annotation.lockClass();
        //解析springEL表达式
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(id);
        StandardEvaluationContext context = new StandardEvaluationContext();
        //添加参数
        Object[] args = pjp.getArgs();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i],args[i].toString());
        }
        //解析参数
        String param = expression.getValue(context).toString();
        //前置增强
        System.out.println("前置增强操作");
        boolean check = IdempotentUtil.check(param, lockClass);
        if (!check) {
            return "请勿重复提交...";
        }
        Object rs = pjp.proceed();
        //后置增强
        System.out.println("后置增强操作");
        return rs;
        //此aop切面处理有问题，改用拦截器处理
        //此问题通过springEL表达式获取动态参数解决，再结合幂等性工具进行判断
    }
}
