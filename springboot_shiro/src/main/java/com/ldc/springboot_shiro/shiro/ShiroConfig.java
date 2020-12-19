package com.ldc.springboot_shiro.shiro;

import com.ldc.springboot_shiro.model.Permission;
import com.ldc.springboot_shiro.model.Role;
import com.ldc.springboot_shiro.service.DemoService;
import com.ldc.springboot_shiro.shiro.filter.SecurityLogoutFilter;
import com.ldc.springboot_shiro.shiro.filter.SecurityPermissionAuthorizationFilter;
import com.ldc.springboot_shiro.shiro.filter.SecurityRoleAuthorizationFilter;
import com.ldc.springboot_shiro.shiro.util.ByteSourceSerializer;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 启动》securityManager》shiroFilterFactoryBean》authorizationAttributeSourceAdvisor
 * cacheManager中缓存authenticationCache和authorizationCache
 * @author: ldc
 * @time: 2020/12/16 21:56
 */
@Configuration
public class ShiroConfig {

    private final String CACHE_KEY = "shiro:cache:";

    private final String SESSION_KEY = "shiro:session:";

    // cacheManager、redisSessionDAO 过期时间
    private final int EXPIRE = 1800;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private DemoService demoService;

    /**
     * @description: 开启Shiro-aop注解支持：使用代理方式所以需要开启代码支持
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * @description: Filter工厂，设置对应的过滤条件和跳转条件
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 自定义过滤器
        Map<String, Filter> filters = new HashMap<>();
        // 角色权限校验
        filters.put("croles", new SecurityRoleAuthorizationFilter());
        // 接口权限校验
        filters.put("cperms", new SecurityPermissionAuthorizationFilter());
        // 用户退出系统
        filters.put("logout", new SecurityLogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);
        // 自定义过滤器链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 顺序判断;authc:所有url都必须认证通过才可以访问;anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/getVerifyCode", "anon");
        filterChainDefinitionMap.put("/getSid", "anon");
        filterChainDefinitionMap.put("/websocket/**", "anon");
        filterChainDefinitionMap.put("/index.html*", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        List<Permission> permissionList = demoService.getAllRolePermissionList();
        // 数据库读取所有角色、权限信息，拦截校验
        for (Permission permission : permissionList) {
            List<Role> roleList = permission.getRoles();
            String roleSet = "";
            for (Role role : roleList) {
                roleSet += role.getRoleName() + ",";
            }
            filterChainDefinitionMap.put(permission.getPermission().startsWith("/") ? permission.getPermission() : "/" + permission.getPermission(),
                    "authc" + ",cperms[" + permission.getPermission() + "]" + ("".equals(roleSet) ? "" : ",croles[" + roleSet.substring(0, roleSet.length() - 1) + "]"));
            System.out.println("shiro config: url" +
                    (permission.getPermission().startsWith("/") ? permission.getPermission() : "/" + permission.getPermission()) +
                    "===" +
                    ("authc" + ",cperms[" + permission.getPermission() + "]" + ("".equals(roleSet) ? "" : ",croles[" + roleSet.substring(0, roleSet.length() - 1) + "]")));
        }
        filterChainDefinitionMap.put("/**", "authc");
        // 登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        // 将filterChainDefinitionMap放入过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @description: 安全管理器
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理
        securityManager.setSessionManager(sessionManager());
        // 自定义Cache实现缓存管理
        securityManager.setCacheManager(cacheManager());
        // 自定义Realm验证
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * @description: 身份验证器，将自己的验证方式加入容器
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCachingEnabled(true);
        myShiroRealm.setAuthenticationCachingEnabled(true);
        myShiroRealm.setAuthorizationCachingEnabled(true);
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * @description: 自定义Realm的加密规则 -> 凭证匹配器：将密码校验交给Shiro的SimpleAuthenticationInfo进行处理,在这里做匹配配置
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用SHA256算法;
        shaCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        shaCredentialsMatcher.setHashIterations(2);
        return shaCredentialsMatcher;
    }

    /**
     * @description: 配置Cache管理器：用于往Redis存储权限和角色标识，使用的是shiro-redis开源插件
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        // 配置缓存的话要求放在session里面的实体类必须有个id标识
        redisCacheManager.setPrincipalIdFieldName("id");
        // 用户权限信息缓存时间 单位秒 此为30分钟
        redisCacheManager.setExpire(EXPIRE);
        return redisCacheManager;
    }

    /**
     * @description: 配置Redis管理器：使用的是shiro-redis开源插件
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setDatabase(redisProperties.getDatabase());
        redisManager.setHost(redisProperties.getHost() + ":" + redisProperties.getPort());
        // 若在此设置密码则不能取空密码，需设置redis密码
        // redisManager.setPassword(redisProperties.getPassword());
        // redisManager.setPort(port);
        return redisManager;
    }

    /**
     * @description: 配置Session管理器
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        shiroSessionManager.setSessionDAO(redisSessionDAO());
        return shiroSessionManager;
    }

    /**
     * @description: 配置RedisSessionDAO ,使用的是shiro-redis开源插件
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        // redisSessionDAO.setValueSerializer(new ByteSourceSerializer());
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        // 此为30分钟
        redisSessionDAO.setExpire(EXPIRE);
        return redisSessionDAO;
    }

    /**
     * @description: SessionID生成器
     * @author: ldc
     * @time: 2020/12/16 21:57
     */
    @Bean
    public ShiroSessionIdGenerator sessionIdGenerator() {
        return new ShiroSessionIdGenerator();
    }

}