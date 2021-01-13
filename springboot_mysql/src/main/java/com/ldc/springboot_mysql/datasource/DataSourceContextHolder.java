package com.ldc.springboot_mysql.datasource;

/**
 * @description: 利用ThreadLocal封装的保存数据源上线的上下文context
 * @author: ldc
 * @time: 2021/1/13 21:39
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    /**
     * 赋值
     */
    public static void set(String datasourceType) {
        context.set(datasourceType);
    }

    /**
     * 获取值
     */
    public static String get() {
        return context.get();
    }

    /**
     * 清空
     */
    public static void clear() {
        context.remove();
    }

}
