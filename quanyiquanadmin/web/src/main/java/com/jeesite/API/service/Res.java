package com.jeesite.API.service;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 接口返回值格式定义.
 * <p>
 * 接口返回均为JSON格式, 形式为: {code: XXX, message:, YYY, data: ZZZ}
 * 其中code为错误码, message为简单的错误描述, data为请求成功时返回的所需数据.
 * 注意: message不能作为前端显示错误的凭据, 该值仅为开发调试使用.
 * <p>
 * Created by xingfinal on 15/11/27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Res<T> {


    private int status;

    private String message;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Res{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
