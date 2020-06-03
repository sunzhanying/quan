package com.jeesite.API.zyapi;


import java.io.Serializable;

/**
 * 修改金额积分返回
 */
public class BaseMemberYeResult implements Serializable {

    private String status;
    private String messages;
    private Integer errorCode;

    private MemberYeResult data;

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

    public MemberYeResult getData() {
        return data;
    }

    public void setData(MemberYeResult data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseMemberYeResult{" +
                "status='" + status + '\'' +
                ", messages='" + messages + '\'' +
                ", errorCode=" + errorCode +
                ", data=" + data +
                '}';
    }
}
