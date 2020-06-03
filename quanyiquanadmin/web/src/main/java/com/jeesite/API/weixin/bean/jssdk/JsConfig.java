package com.jeesite.API.weixin.bean.jssdk;

import java.io.Serializable;

/**
 * JS SDK Config
 *
 * @author Shixing
 */
public class JsConfig implements Serializable {

    private boolean debug;
    private String appId;
    private Long timestamp;
    private String nonceStr;
    private String signature;
    private String[] jsApiList;
    private Long accountId;
    public JsConfig() {
    }
    public JsConfig(boolean debug, String appId, Long timestamp, String nonceStr, String signature, String[] jsApiList) {
        this.debug = debug;
        this.appId = appId;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signature = signature;
        this.jsApiList = jsApiList;
    }
    public JsConfig(boolean debug, String appId, Long timestamp, String nonceStr, String signature, String[] jsApiList,Long accountId) {
        this.debug = debug;
        this.appId = appId;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signature = signature;
        this.jsApiList = jsApiList;
        this.accountId=accountId;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String[] getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(String[] jsApiList) {
        this.jsApiList = jsApiList;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
