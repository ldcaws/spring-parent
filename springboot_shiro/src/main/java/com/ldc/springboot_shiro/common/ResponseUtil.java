package com.ldc.springboot_shiro.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/19 21:46
 */
public class ResponseUtil {

    public static void returnResultAjax(HttpServletResponse response, String returnInfo, String encoding) {
        if (null == encoding || "".equals(encoding)) encoding = "UTF-8";
        PrintWriter out = null;
        response.setCharacterEncoding(encoding);
        try {
            out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.print(returnInfo);
            out.flush();
            out.close();
        }
    }

    public static void returnResultAjaxByte(HttpServletResponse response, byte[] returnInfo) {
        OutputStream out = null;
        response.setCharacterEncoding("UTF-8");
        try {
            out = response.getOutputStream();
            if (returnInfo != null) {
                out.write(returnInfo, 0, returnInfo.length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
