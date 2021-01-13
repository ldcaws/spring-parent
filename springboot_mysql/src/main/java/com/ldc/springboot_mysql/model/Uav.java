package com.ldc.springboot_mysql.model;

import java.io.Serializable;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/13 21:18
 */
public class Uav implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

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

}
