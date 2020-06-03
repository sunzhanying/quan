package com.jeesite.API.zyapi;


import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

public class BaseResult implements Serializable {

    private String status;
    private String messages;
    private Integer errorCode;
    //private T data;

    private MemberResult data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public MemberResult getData() {
        return data;
    }

    public void setData(MemberResult data) {
        this.data = data;
    }

   /* public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }*/
}
