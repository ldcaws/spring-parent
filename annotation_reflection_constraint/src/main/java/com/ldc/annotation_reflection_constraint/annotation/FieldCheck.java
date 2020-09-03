package com.ldc.annotation_reflection_constraint.annotation;

import com.ldc.annotation_reflection_constraint.validator.FieldCheckConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义字段校验注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldCheckConstraint.class)
public @interface FieldCheck {

    // 合法的参数值
    String[] paramValues();

    // 提示信息
    String message() default "参数不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
