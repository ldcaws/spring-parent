package com.ldc.springboot_mysql.datasource;

import java.lang.annotation.*;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/13 21:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceAnnotation {
    /**
     * 默认数据源
     */
    DataSourceEnum value() default DataSourceEnum.MASTER;
    /**
     * 清除
     */
    boolean clear() default true;
}