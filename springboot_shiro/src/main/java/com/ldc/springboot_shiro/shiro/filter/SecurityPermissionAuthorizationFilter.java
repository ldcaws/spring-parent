package com.ldc.springboot_shiro.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/16 22:25
 */
public class SecurityPermissionAuthorizationFilter extends PermissionsAuthorizationFilter {
    private Logger logger = LoggerFactory.getLogger(SecurityPermissionAuthorizationFilter.class);

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[]) ((String[]) mappedValue);
        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else if (!subject.isPermittedAll(perms)) {
                isPermitted = false;
            }
        } else {
            // 若数据库未配置URL，则通过这种方式判断
            /*permissionService = (PermissionService) SpringBeanUtil.getBean("permissionService");
            List<SysRolePermission> permissionList = permissionService.getAllRolePermissionList();
            String currentContextPath = ((HttpServletRequest) request).getContextPath();
            String currentReqUrl = ((HttpServletRequest) request).getRequestURI();
            String reqPathInfo = currentReqUrl.replace(currentContextPath, "").replace("//", "/");
            isPermitted = false;
            for(SysRolePermission permission:permissionList){
                if (permission.getUrl() != null && permission.getUrl().trim().equals(reqPathInfo)) {
                    isPermitted = true;
                }
            }*/
        }
        return isPermitted;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        System.out.println("越权访问-接口");
        System.out.println(((HttpServletRequest) request).getRequestURI());
        HttpServletResponse resp = (HttpServletResponse) response;
        /*String accessKey = AccessKeyUtil.getAesKey((HttpServletRequest) request);
        String ip = IPUtil.getIpAddress((HttpServletRequest) request);
        String url = ((HttpServletRequest) request).getRequestURL() + "";
        if(ShiroConfig.getSecurityState()) {
        String json = JSONUtil.object2JSONStr(ResponseData.fail(ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getMsg(), ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getCode()));
        ResponseUtil.returnResultAjaxByte(resp,json.getBytes("UTF-8"));
        }else{
            ResponseUtil.returnResultAjax(resp, JSONUtil.object2JSONStr(ResponseData.fail(ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getMsg(), ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getCode())), null);
        }
        LogManager.getInstance().insertLog(LogConstant.LOG_EVENTTYPE_SYSTEMLOG, ResponseCode.SYS_VALIDATE_ExceedAuthorizedAccess.getMsg(), LogConstant.LOG_OPERATION_TYPE_UNAUTHORIZED, LogConstant.LOG_EVENTLEVEL_HIGH, LogConstant.LOG_OPERATION_RESULT_FAILURE, ip, url);*/
        logger.error("接口权限验证：无【" + ((HttpServletRequest) request).getRequestURI() + "】访问权限");
        return false;
    }
}
