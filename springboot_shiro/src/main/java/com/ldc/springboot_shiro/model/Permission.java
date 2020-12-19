package com.ldc.springboot_shiro.model;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/16 21:35
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String permission;
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}