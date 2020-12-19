package com.ldc.springboot_shiro.shiro.filter;

import com.ldc.springboot_shiro.common.IPUtil;
import com.ldc.springboot_shiro.common.ResponseUtil;
import com.ldc.springboot_shiro.model.User;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/19 22:56
 */
public class SecurityLogoutFilter extends LogoutFilter {

    private static final Logger log = LoggerFactory.getLogger(SecurityLogoutFilter.class);

    @Value("${safe.switch}")
    private String safe;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        Subject subject = getSubject(request, response);
        User currentUser = (User) subject.getPrincipal();
        resp.setCharacterEncoding("UTF-8");
        String ip = IPUtil.getIpAddress((HttpServletRequest) request);
        String url = ((HttpServletRequest) request).getRequestURL() + "";
        if (currentUser == null) {
            log.info("用户未登录，无法退出系统");
            ResponseUtil.returnResultAjax(resp, "用户未登录，无法退出系统", null);
        } else {
            logout(subject);
            log.info("用户" + currentUser.getName() + "正常退出！");
            if("on".equals(safe)) {
                ResponseUtil.returnResultAjax(resp, "退出登录成功", null);
            }else {
                ResponseUtil.returnResultAjax(resp, "退出登录成功", null);
            }
        }
        return false;
    }

    public static void logout(Subject subject) {
        if (subject == null) return;
        subject.logout();
    }
}
