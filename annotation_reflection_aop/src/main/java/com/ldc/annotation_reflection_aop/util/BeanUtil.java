package com.ldc.annotation_reflection_aop.util;

import com.ldc.annotation_reflection_aop.annotation.SetValueField;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

/**
 * @description:增强的实现过程
 * @author: ss
 * @time: 2020/9/2 21:11
 */
@Component
public class BeanUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setFieldValue(Collection collection) throws Exception {
        //1获取结果集中的对象
        //2获取该对象的类元信息
        //3通过类元信息获取该对象的所有字段
        //4遍历所有字段
        //5有注解的字段则需要遍历结果集，将每个对象的该字段赋值，此时需要结果集中对象及目标对象的目标字段的值
        //6结果集中对象已有，目标对象需要通过下面获取

        //1通过注解获取目标对象的实例
        //2通过注解获取目标对象的目标方法名
        //3通过注解获取结果集中对象的参数字段名
        //4通过注解获取目标对象的目标字段名
        //5通过注解获取目标对象的目标方法
        //6目标对象的目标方法通过目标对象和参数值利用反射获取目标对象的结果对象
        //7目标字段通过目标对象的结果对象利用反射获取值

        //缓存
        HashMap<Object, Object> cache = new HashMap<>();
        //1获取结果集中的对象的类元信息
        Class<?> clazz = collection.iterator().next().getClass();//order类元信息
        //2通过类元信息获取该对象的所有字段
        Field[] declaredFields = clazz.getDeclaredFields();
        //3遍历所有字段
        for (Field declaredField : declaredFields) {
            //3获取每个字段上的注解
            SetValueField annotation = declaredField.getAnnotation(SetValueField.class);
            if (annotation == null) continue;
            //4设置该有注解的字段可见，为赋值做准备
            declaredField.setAccessible(true);

            //①通过注解获取目标对象实例
            Object bean = this.applicationContext.getBean(annotation.beanClass());
            //②通过注解获取目标对象的目标方法名
            String methodName = annotation.method();
            //③通过注解获取结果集中对象的参数字段名
            String paramFieldName = annotation.paramField();
            Field paramField = clazz.getDeclaredField(paramFieldName);
            paramField.setAccessible(true);
            //⑤通过注解获取目标对象的目标字段名
            String targetFieldName = annotation.targetField();
            Field targetField = null;
            //⑥通过注解获取目标对象的目标方法
            //Method method = annotation.beanClass().getMethod(methodName, clazz.getDeclaredField(paramName).getType());
            Method method = bean.getClass().getMethod(methodName, paramField.getType());
            //⑦目标对象的目标方法通过目标对象和参数值利用反射获取目标对象的结果对象

            Boolean flag = StringUtils.isEmpty(targetFieldName);
            String keyPrefix = annotation.beanClass()+"-"+annotation.method()+"-"+annotation.targetField()+"-";
            //5有注解的字段则需要遍历结果集，将每个对象的该字段赋值
            for (Object obj : collection) {
                Object paramValue = paramField.get(obj);
                if (paramValue == null) continue;
                //目标对象的结果对象及结果对象的目标字段值
                Object resultBean = null;
                Object targetFieldValue = null;
                String key = keyPrefix+paramValue;
                if (cache.containsKey(key)) {
                    targetFieldValue = cache.get(key);//缓存拿目标对象的结果对象
                } else {
                    resultBean = method.invoke(bean, paramValue);//user类元信息
                    if (!flag) {
                        if (resultBean != null) {
                            if (targetField == null) {
                                targetField = resultBean.getClass().getDeclaredField(targetFieldName);
                                targetField.setAccessible(true);
                            }
                            targetFieldValue = targetField.get(resultBean);
                        }
                    }
                    cache.put(key,targetFieldValue);
                }
                Object currentNeedVlaue = declaredField.get(obj);
                if (currentNeedVlaue == null){
                    declaredField.set(obj,targetFieldValue);
                }
            }
        }

    }

}
