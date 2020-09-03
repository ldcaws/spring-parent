package com.ldc.annotation_reflection_aop.model;

import com.ldc.annotation_reflection_aop.annotation.SetValueField;
import com.ldc.annotation_reflection_aop.mapper.UserMapper;

import java.io.Serializable;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/2 21:43
 */
public class Order implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;

    private int customId;

    //beenClass:反射需要知道的类
    //param:反射方法的入参 （因为我们要拿到方法，拿到方法除了知道方法名字外还需要知道方法的入参数类型）
    //method:反射执行所需的方法名
    //targetFiled：指的是dao层的返回user对象结果中的的所需字段名字，可以通过该名字拿到具体的值
    @SetValueField(beanClass = UserMapper.class,paramField = "customId",method = "findUserById",targetField = "name")
    private String customName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

}
