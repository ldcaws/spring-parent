package com.ldc.springboot_shiro.shiro;

import com.ldc.springboot_shiro.model.Permission;
import com.ldc.springboot_shiro.model.Role;
import com.ldc.springboot_shiro.model.User;
import com.ldc.springboot_shiro.service.DemoService;
import com.ldc.springboot_shiro.shiro.util.ByteSourceUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 实现AuthorizingRealm接口用户认证
 * @author: ldc
 * @time: 2020/12/16 21:42
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private DemoService demoService;

    /**
     * @description: 角色权限和对应权限添加
     * @author: ldc
     * @time: 2020/12/16 21:45
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取当前登录用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.print(user);
        // 获取当前登录用户信息
        User user2 = (User) principalCollection.getPrimaryPrincipal();
        // 根据用户名称去数据库查询用户
        User user3 = demoService.getUserByName(user2.getName());
        // 添加角色和权限
        for (Role role : user3.getRoles()) {
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * @description: 用户认证，认证之后进行上面的授权
     * @author: ldc
     * @time: 2020/12/16 21:46
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取当前登录用户信息
        String name = authenticationToken.getPrincipal().toString();
        // 根据用户名称去数据库查询用户
        User user = demoService.getUserByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    user,
                    user.getPassword(),
                    // 报序列化错误
                    // ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                    ByteSourceUtils.bytes(user.getCredentialsSalt()),//salt=username+salt
                    getName()
            );
            return simpleAuthenticationInfo;
        }
    }

    /**
     * @description: 自定义方法：清除所有 授权缓存
     * @author: ldc
     * @time: 2020/12/16 21:46
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * @description: 自定义方法：清除所有 认证缓存
     * @author: ldc
     * @time: 2020/12/16 21:46
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * @description: 自定义方法：清除所有的  认证缓存  和 授权缓存
     * @author: ldc
     * @time: 2020/12/16 21:46
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}