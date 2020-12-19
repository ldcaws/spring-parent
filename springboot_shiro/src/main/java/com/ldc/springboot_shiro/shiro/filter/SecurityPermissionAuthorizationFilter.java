package com.ldc.springboot_shiro.shiro.filter;

import com.ldc.springboot_shiro.common.IPUtil;
import com.ldc.springboot_shiro.common.ResponseUtil;
import com.ldc.springboot_shiro.common.SpringBeanUtil;
import com.ldc.springboot_shiro.model.Permission;
import com.ldc.springboot_shiro.service.DemoService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/16 22:25
 */
public class SecurityPermissionAuthorizationFilter extends PermissionsAuthorizationFilter {

    private Logger logger = LoggerFactory.getLogger(SecurityPermissionAuthorizationFilter.class);

    @Value("${safe.switch}")
    private String safe;

    private DemoService demoService;

    /**
     * @description: 是否允许访问
     * @author: ldc
     * @time: 2020/12/19 21:32
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[]) mappedValue;
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
            demoService = (DemoService) SpringBeanUtil.getBean("demoService");
            List<Permission> permissionList = demoService.getAllRolePermissionList();
            String currentContextPath = ((HttpServletRequest) request).getContextPath();
            String currentReqUrl = ((HttpServletRequest) request).getRequestURI();
            String reqPathInfo = currentReqUrl.replace(currentContextPath, "").replace("//", "/");
            isPermitted = false;
            for(Permission permission:permissionList){
                if (permission.getPermission() != null && permission.getPermission().trim().equals(reqPathInfo)) {
                    isPermitted = true;
                }
            }
        }
        return isPermitted;
    }

    /**
     * @description: 不允许访问时的处理
     * @author: ldc
     * @time: 2020/12/19 21:32
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        System.out.println("进入url越权访问");
        System.out.println(((HttpServletRequest) request).getRequestURI());
        HttpServletResponse resp = (HttpServletResponse) response;
        String ip = IPUtil.getIpAddress((HttpServletRequest) request);
        String url = ((HttpServletRequest) request).getRequestURL() + "";
        if("on".equals(safe)) {
            String json ="url越权访问:" + url;
            ResponseUtil.returnResultAjaxByte(resp,json.getBytes("UTF-8"));
        }else{
            String json ="url越权访问:" + url;
            ResponseUtil.returnResultAjax(resp,json, null);
        }
        logger.error("接口权限验证：无【" + ((HttpServletRequest) request).getRequestURI() + "】访问权限");
        return false;
    }
}
