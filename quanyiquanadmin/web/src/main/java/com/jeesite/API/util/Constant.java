package com.jeesite.API.util;

/**
 * Created by xingfinal on 15/11/28.
 */
public class Constant {


    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    // 商户私钥
    public static final String BUSINESS_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL9c2e5ZzEsH88HVMy7PIPrqXDVce3x3Gae6jd+EQy421Zc1fwJL1HcbjE7zImyureOc6bZEgQeIoS8oAGrlGnHqo2OQAKFS0qNtntO/z4sfwwKMRI5D0E1yDlotizkmuT6iaLMBdMVaX/ah26UlwEn7QqXJ0NldDCgOTQ4CWfAjAgMBAAECgYBxNUoMMv2kCcFQE4PjFlBpgIoqW1sGiCMaUkgqSkHALlpvyQspXZkFGbwI3NepujFPLX4qAR7gSRkqH50XoA/i/GyTjhkFA18SvUv814bTFQf07UR7Dj1Zkfb7kI8O/xJYPOLwVIDd68sp1vhQFLvAFjunCvMN4JssA9/ZoQZH6QJBAOmXjfpETtfb4Syx+qK3YUC674upkcN032L4k1QDMNunqR8uUtM7VV7O6oKg9u6cAjkmctaa0C/5u1S1JX93IXUCQQDRuD+phvoSZnV5E+xI9aBjG8pqXheuQERXA1ub4FDU4ABCfgb+HVHQ/0+nwX2128uO7SiIiM+D8hYaALm8wsA3AkAXgaPM8oDIohzXxZVGOJP10pn2gPrlI2aza/ZPdv68q/ON6rh0/zyJDON2f8I5osNqgt79j5ixqzzZulzqpc9dAkA/hGc7qBbLRd7kaeVRf5sxCTS4HIFdlDdehzTStBCnOKS7fsINasHh48GzVEYHs2cmhauWFqGLG+IXxAp9IhrnAkB9lJminmq3jwImG0oWLE+/Uh34AjqO5XhHGyvnQLjMsfynGBle0Ua+sLRetOhqw3wx3L1aLJAS7TYsFOOZlBRI";

    // 支付宝验证URL*
    public static final String ALIPAY_VERIFY_URL = "https://mapi.alipay.com/gateway.do";

    // 支付宝支付结果通知URL*
    public static final String ALIPAY_NOTIFY_URL = "http://wx.changaikang.com:12002/api/order-record/ali/notify";

    // 支付宝移动支付Service
    public static final String ALIPAY_SERVICE_MOBILE = "mobile.securitypay.pay";

    // 支付宝即时到账*
    public static final String ALIPAY_SERVICE_DIRECT = "create_direct_pay_by_user";

    // 支付宝即时到账退款
    public static String ALIPAY_SERVICE_REFUND = "refund_fastpay_by_platform_pwd";

    // 支付宝 partner ID
    public static final String ALIPAY_PARTNER_ID = "2088221611505454";

    // 支付宝签名字符集*
    public static final String ALIPAY_CHARSET = "UTF-8";

    // 支付宝MD5尾串
    public static final String ALIPAY_MD5_TAIL = "t11iagsac4e8tctuudzwmsu8cp7ovlt9";

    //肠愛康接口
    // 悦可短信发送接口
//    public static final String SMS_SEND_URL = "http://111.1.1.146:8823/SendSms.aspx";
//
//    // 悦可短信发送账户
//    public static final String SMS_SEND_USERNAME = "CAK";
//
//    // 悦可短信发送密码: 用户名 + API密码, 并进行md5加密
//    public static final String SMS_SEND_PASSWORD = "b8bbc48e6f0f9fe5b5119d1ba9ae71cf";

    //Evcard社区
    // 悦可短信发送接口
    public static final String SMS_SEND_URL = "http://www.shizhicom.cn:8080/SendMessage/SendSms_API";

    // 悦可短信发送账户账号 XIAOGL密码ERRERRERR
    public static final String SMS_SEND_USERNAME = "QJ";

    // 悦可短信发送密码: 用户名 + API密码, 并进行md5加密 用户名EVCARD密码211262
    public static final String SMS_SEND_PASSWORD = "D893AC655E02CA6587D1F1A0F775541A";

    //奧易短信开发
    public static final String AOYI_SMS_SEND_URL = "http://59.110.0.201:6085/ayht-interface/sendsms";

    //奧易短信公钥
    public static final String AOYI_SMS_SEND_PUBLIC_KEY = "AYHT-4800-0BE6-852A";

    // 奧易AES私钥
    public static final String AOYI_SEND_PRIVATE_KEY = "BE6852A358FA4EA1";

    public static final String SMS_TEMPLATE = "您正在使用迁乔服务进行短信认证，您的验证码是:%s，请在1分钟内完成验证。";

    public static final String SMS_TEMPLATE_INTERVIEW="dsfdsdsfddsds";

    // JWT Token Attribute
    public static final String CLAIMS = "claims";

    public static final String JWT_CLAIMS_USERID = "userId"; // 用户ID
    public static final String JWT_CLAIMS_ROLES = "roles"; // 用户角色
    public static final String JWT_CLAIMS_OPENID = "openId"; // 微信OpenId

}
