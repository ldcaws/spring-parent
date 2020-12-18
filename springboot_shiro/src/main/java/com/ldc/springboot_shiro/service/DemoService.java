package com.ldc.springboot_shiro.service;

import com.ldc.springboot_shiro.model.Permission;
import com.ldc.springboot_shiro.model.Role;
import com.ldc.springboot_shiro.model.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/17 22:28
 */
@Service
public class DemoService {

    public User getUserByName(String name) {
        User user = new User();
        user.setId(1L);
        user.setName("caocao");
        user.setPassword(new SimpleHash("md5", "1", user.getCredentialsSalt(), 2).toString());
        // user.setPassword("1");
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("角色名称");
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermission("权限");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return user;
    }
}
