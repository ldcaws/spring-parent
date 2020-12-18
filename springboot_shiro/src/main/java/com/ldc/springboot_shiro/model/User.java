package com.ldc.springboot_shiro.model;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/16 21:33
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String password;
    private List<Role> roles;
    private String salt="edaa06566a1e11e998ea5065f327c9f3";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.name+this.salt;
    }
}