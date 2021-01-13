package com.ldc.springboot_mysql.datasource;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/13 21:38
 */
public enum DataSourceEnum {

    //数据源主库、从库
    MASTER("1","master"),
    SLAVE("2","slave");

    private String code;
    private String name;

    private DataSourceEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    public static DataSourceEnum getByCode(String code) {
        for (DataSourceEnum event : DataSourceEnum.values()) {
            if (event.code.equals(code)) {
                return event;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
