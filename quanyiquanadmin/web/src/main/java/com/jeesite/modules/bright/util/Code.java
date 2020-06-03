package com.jeesite.modules.bright.util;

/**
 * 错误码表.
 * <p>
 * Created by xingfinal on 15/11/28.
 */
public enum Code {
    // 1024以内, 同HTTP
    SUCCESS(200, "Success."),
    BAD_REQUEST(400, "错误的请求"),
    UNKNOWN_ERROR(900, "未知错误"),
    API_USER_ROLE_ERROR(900, "不存在该权限"),
    API_IS_USED(901, "信息失效"),
    API_NOT_EXITS(902, "不存在"),
    API_PHONE_ERROR(903, "手机号授权失败"),

    API_SEND_SMS_ERROR(70002, "短信发送失败"),
    API_VERIFY_SMS_ERROR(70003, "短信校验失败"),
    API_PHONE_NOTNULL(70004, "您已绑定该手机号"),
    API_QRCODE_ERROR(700019,"二维码生成失败");
    private long code;
    private String message;

     Code(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
