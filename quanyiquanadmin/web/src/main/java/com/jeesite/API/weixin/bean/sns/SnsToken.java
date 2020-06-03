package com.jeesite.API.weixin.bean.sns;


import com.jeesite.API.weixin.bean.BaseResult;

public class SnsToken extends BaseResult {

    private String openid;
    private String session_Key;
    private String unionid;

    public String getSession_Key() {
        return session_Key;
    }

    public void setSession_Key(String session_Key) {
        this.session_Key = session_Key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }



    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "SnsToken{" +
                "openid='" + openid + '\'' +
                ", session_Key='" + session_Key + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }

  /*   private String access_token;

    private Integer expires_in;

    private String refresh_token;

    private String openid;

    private String scope;

    private String unionid;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String accessToken) {
        access_token = accessToken;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expiresIn) {
        expires_in = expiresIn;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refreshToken) {
        refresh_token = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnsToken snsToken = (SnsToken) o;
        return Objects.equals(access_token, snsToken.access_token) &&
                Objects.equals(expires_in, snsToken.expires_in) &&
                Objects.equals(refresh_token, snsToken.refresh_token) &&
                Objects.equals(openid, snsToken.openid) &&
                Objects.equals(scope, snsToken.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access_token, expires_in, refresh_token, openid, scope);
    }

    @Override
    public String toString() {
        return "SnsToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }*/
}
