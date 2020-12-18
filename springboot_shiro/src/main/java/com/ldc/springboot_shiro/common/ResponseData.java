package com.ldc.springboot_shiro.common;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/17 22:17
 */
public class ResponseData<T> {
    //状态
    private Boolean status = true;
    //响应吗
    private int code = 200;
    //消息
    private String message;
    //返回的数据
    private T data;

    public static ResponseData ok(Object data) {
        return new ResponseData(data);
    }

    public static ResponseData ok(Object data,String message) {
        return new ResponseData(data,message);
    }

    public static ResponseData fail(String message,int code) {
        ResponseData responseData= new ResponseData();
        responseData.setCode(code);
        responseData.setMessage(message);
        responseData.setStatus(false);
        responseData.setData(null);
        return responseData;
    }

    public static ResponseData fail(String message) {
        ResponseData responseData= new ResponseData();
        responseData.setCode(400);
        responseData.setMessage(message);
        responseData.setStatus(false);
        responseData.setData(null);
        return responseData;
    }

    public ResponseData(T data) {
        super();
        this.data = data;
    }
    public ResponseData(T data,String message) {
        super();
        this.data = data;
        this.message=message;
    }
    public ResponseData() {
        super();
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
