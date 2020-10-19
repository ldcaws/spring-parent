package com.ldc.springboot_response.response;

/**
 * @description:统一的接口返回格式类
 * @author: ss
 * @time: 2020/7/3 23:28
 */
public class ResponseResult<T> {

    private Boolean status = true; //状态
    private Integer code = 200; //响应吗
    private String message;//消息
    private T data;//返回的数据

    public ResponseResult() {
        super();
    }

    public ResponseResult(T data) {
        super();
        this.data = data;
    }

    public ResponseResult(T data, String message) {
        super();
        this.data = data;
        this.message=message;
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(data);
    }

    public static ResponseResult success(Object data, String message) {
        return new ResponseResult(data,message);
    }

    public static ResponseResult fail(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setStatus(false);
        responseResult.setCode(400);
        responseResult.setMessage(message);
        responseResult.setData(null);
        return responseResult;
    }

    public static ResponseResult fail(String message, int code) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setStatus(false);
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(null);
        return responseResult;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
