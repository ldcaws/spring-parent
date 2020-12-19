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
        role.setRoleName("admin");
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermission("/getUser1");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return user;
    }

    public List<Permission> getAllRolePermissionList() {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermission("/getUser1");
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("admin");
        roles.add(role);
        permission.setRoles(roles);
        permissions.add(permission);
        Permission permission2 = new Permission();
        permission2.setId(2L);
        permission2.setPermission("/getUser2");
        List<Role> roles2 = new ArrayList<>();
        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("admin");
        roles2.add(role2);
        permission2.setRoles(roles2);
        permissions.add(permission2);
        return permissions;
    }
}
