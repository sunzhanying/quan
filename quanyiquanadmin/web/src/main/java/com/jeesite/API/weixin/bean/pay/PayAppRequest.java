package com.jeesite.API.weixin.bean.pay;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 支付 JS 请求
 *
 * @author LiYi
 */
public class PayAppRequest {

    private String appid;

    private String timestamp;

    private String partnerid;

    private String prepayid;

    private String noncestr;

    @JSONField(name = "package")
    private String package_;



    private String sign;



    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackage_() {
        return package_;
    }

    public void setPackage_(String package1) {
        package_ = package1;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
