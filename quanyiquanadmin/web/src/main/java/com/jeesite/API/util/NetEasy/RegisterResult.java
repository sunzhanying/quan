package com.jeesite.API.util.NetEasy;

/**
 * 网易请求状态数据
 *
 * @author lijinhui
 */
public class RegisterResult  {


    protected String token;
    protected String accid;
    protected String name;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "token='" + token + '\'' +
                ", accid='" + accid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
