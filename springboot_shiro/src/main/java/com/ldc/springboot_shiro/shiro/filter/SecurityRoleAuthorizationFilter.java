package com.ldc.springboot_shiro.shiro.filter;

import com.ldc.springboot_shiro.common.IPUtil;
import com.ldc.springboot_shiro.common.ResponseUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/16 22:29
 */
public class SecurityRoleAuthorizationFilter extends RolesAuthorizationFilter {

    private Logger logger = LoggerFactory.getLogger(SecurityRoleAuthorizationFilter.class);

    @Value("${safe.switch}")
    private String safe;

    /**
     * @description: 是否允许访问
     * @author: ldc
     * @time: 2020/12/19 21:32
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray != null && rolesArray.length != 0) {
            Set<String> roles = CollectionUtils.asSet(rolesArray);
            for (String role : roles) {
                if (subject.hasRole(role)) {
                    return true;
                }
            }
            return subject.hasAllRoles(roles);
        } else {
            return true;
        }
    }

    /**
     * @description: 不允许访问时的处理
     * @author: ldc
     * @time: 2020/12/19 21:32
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        System.out.println("进入角色越权访问");
        System.out.println(((HttpServletRequest) request).getRequestURI());
        HttpServletResponse resp = (HttpServletResponse) response;
        String ip = IPUtil.getIpAddress((HttpServletRequest) request);
        String url = ((HttpServletRequest) request).getRequestURL() + "";
        if ("on".equals(safe)) {
            String json ="角色越权访问:" + ip;
            ResponseUtil.returnResultAjaxByte(resp,json.getBytes("UTF-8"));
        } else {
            String json ="角色越权访问:" + ip;
            ResponseUtil.returnResultAjax(resp, json, null);
        }
        logger.error("角色权限验证：无【" + ((HttpServletRequest) request).getRequestURI() + "】访问权限");
        return false;
    }
}
