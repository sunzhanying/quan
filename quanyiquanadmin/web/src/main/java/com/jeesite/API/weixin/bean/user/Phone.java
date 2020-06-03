package com.jeesite.API.weixin.bean.user;

import com.jeesite.API.weixin.bean.BaseResult;

/**
 * 手机号
 */
public class Phone extends BaseResult {

    private String countryCode;
    private String phoneNumber;
    private String purePhoneNumber;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }
}
