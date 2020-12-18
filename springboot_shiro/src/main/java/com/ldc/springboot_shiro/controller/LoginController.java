package com.ldc.springboot_shiro.controller;

import com.ldc.springboot_shiro.common.GuidGenerator;
import com.ldc.springboot_shiro.common.IPUtil;
import com.ldc.springboot_shiro.common.ResponseData;
import com.ldc.springboot_shiro.common.VerifyCodeUtils;
import com.ldc.springboot_shiro.model.User;
import com.ldc.springboot_shiro.shiro.MyShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/17 21:47
 */
@RestController
@RequestMapping
public class LoginController {

    @Value("${safe.switch}")
    private String safe;

    @Autowired
    private MyShiroRealm myShiroRealm;

    @RequestMapping(value = "/getGuid", method = RequestMethod.POST, produces = "application/json")
    public Object getGuid() {
        String guid = GuidGenerator.generate();
        System.out.println("随机数：" + guid);
        return ResponseData.ok(guid);
    }

    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET, produces = "application/json")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String verCodeKey = request.getParameter("key");
        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        System.out.println("验证码【" + verifyCode + "】的key为【" + verCodeKey + "】");
        //redisService.set(verCodeKey, verifyCode, 2 * 60 * 60);
        //生成图片
        int w = 200, h = 80;
        try {
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {
        String username = params.get("name");
        String pwd = params.get("pwd");
        String plainPwd = "";
        if ("on".equals(safe)) {
            //decrypt
            plainPwd = pwd;
        } else {
            plainPwd = pwd;
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            System.out.println("登录前sessionId："+subject.getSession().getId());
            // 让旧session失效
            subject.logout();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, plainPwd);
            // 在此清除认证和授权缓存，需去数据库重新获取用户信息
            // myShiroRealm.clearAllCache();
            subject.login(usernamePasswordToken);
            System.out.println("登陆成功后sessionId：" + subject.getSession().getId());
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Map<String, String> returnData = new HashMap<>();
            if (user != null) {
                returnData.put("userId", user.getId().toString());
                returnData.put("userName", user.getName());
                returnData.put("token", subject.getSession().getId().toString());
                // sessionId与登录用户的对应关系
                subject.getSession().setAttribute(subject.getSession().getId().toString(), returnData);
                // 记录登录用户名与IP之间的关系
                subject.getSession().setAttribute(user.getName(), IPUtil.getIpAddress(request));
                // 为了让请求头中的Cookie失效
                Cookie[] cookies= request.getCookies();
                if(cookies!=null){
                    for (Cookie cookie :cookies){
                        cookie.setPath("/");
                        cookie.setHttpOnly(true);
                        cookie.setMaxAge(0);
                        cookie.setValue("");
                        response.addCookie(cookie);
                    }
                }
            }
            return ResponseData.ok(returnData, "登录成功");
        } catch (AuthenticationException ce) {
            if (ce instanceof UnknownAccountException) {
                return ResponseData.fail("用户不存在", 1001);
            } else if (ce instanceof IncorrectCredentialsException) {
                return ResponseData.fail("密码错误", 1002);
            } else if (ce instanceof LockedAccountException) {
                return ResponseData.fail("用户被锁定", 1003);
            } else if (ce instanceof ExcessiveAttemptsException) {
                return ResponseData.fail("非法尝试", 1004);
            } else {
                return ResponseData.fail("登录出现异常", 999);
            }
        }
    }
}
