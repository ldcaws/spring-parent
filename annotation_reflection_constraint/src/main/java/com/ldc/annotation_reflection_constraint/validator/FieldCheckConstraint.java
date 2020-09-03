package com.ldc.annotation_reflection_constraint.validator;

import com.ldc.annotation_reflection_constraint.annotation.FieldCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/3 16:12
 */
public class FieldCheckConstraint implements ConstraintValidator<FieldCheck,Object> {

    // 从注解中获取合法的参数值
    private List<String> paramValues;

    @Override
    public void initialize(FieldCheck constraintAnnotation) {
        // 初始化时获取注解上的参数值
        paramValues = Arrays.asList(constraintAnnotation.paramValues());
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (paramValues.contains(o)) {
            return true;
        }
        // 若不在参数值中则false
        return false;
    }
}
