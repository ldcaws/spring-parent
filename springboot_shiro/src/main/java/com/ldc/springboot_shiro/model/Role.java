package com.ldc.springboot_shiro.model;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/16 21:34
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String roleName;
    private User user;
    private List<Permission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}