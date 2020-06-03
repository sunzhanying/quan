package com.jeesite.API.util.NetEasy;


import java.io.Serializable;

/**
 * 网易请求状态数据
 *
 * @author lijinhui
 */
public class BasicResult<T>  implements Serializable {

    private String code;
    private T info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public boolean isSuccess() {
        return "200".equals(code);
    }

    @Override
    public String toString() {
        return "BasicResult{" +
                "code='" + code + '\'' +
                ", info=" + info +
                '}';
    }
}
