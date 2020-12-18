package com.ldc.springboot_shiro.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[]) ((String[]) mappedValue);
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

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        System.out.println("越权访问-角色");
        System.out.println(((HttpServletRequest) request).getRequestURI());
        HttpServletResponse resp = (HttpServletResponse) response;
        /*String accessKey = AccessKeyUtil.getAesKey((HttpServletRequest) request);
        String ip = IPUtil.getIpAddress((HttpServletRequest) request);
        String url = ((HttpServletRequest) request).getRequestURL() + "";
        if (ShiroConfig.getSecurityState()) {
        String json = JSONUtil.object2JSONStr(ResponseData.fail(ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getMsg(), ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getCode()));
        ResponseUtil.returnResultAjaxByte(resp,json.getBytes("UTF-8"));
        } else {
            ResponseUtil.returnResultAjax(resp, JSONUtil.object2JSONStr(ResponseData.fail(ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getMsg(), ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getCode())), null);
        }
        LogManager.getInstance().insertLog(LogConstant.LOG_EVENTTYPE_SYSTEMLOG, ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getMsg(), LogConstant.LOG_OPERATION_TYPE_UNAUTHORIZED, LogConstant.LOG_EVENTLEVEL_HIGH, LogConstant.LOG_OPERATION_RESULT_FAILURE, ip, url);*/
        logger.error("角色权限验证：无【" + ((HttpServletRequest) request).getRequestURI() + "】访问权限");
        return false;
    }
}
