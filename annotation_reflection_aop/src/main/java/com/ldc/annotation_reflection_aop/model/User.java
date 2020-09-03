package com.ldc.annotation_reflection_aop.model;

import java.io.Serializable;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/2 22:09
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String sex;

    private int age;

    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
