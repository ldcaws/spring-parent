package com.ldc.springboot_shiro.shiro;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/16 22:49
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

    /**
     * 重写构造器
     */
    public ShiroSessionManager() {
        super();
        this.setDeleteInvalidSessions(true);
    }

    /**
     * 重写方法实现从请求头获取Token便于接口统一
     * 每次请求进来,Shiro会去从请求头找REQUEST_HEADER这个key对应的Value(Token)
     */
    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader("Authorization");
        // 如果请求头中存在token 则从请求头中获取token
        if (!StringUtils.isEmpty(token)) {
            return token;
        } else {
            // 否则按默认规则从cookie取token
            return super.getSessionId(request, response);
        }
    }
}
