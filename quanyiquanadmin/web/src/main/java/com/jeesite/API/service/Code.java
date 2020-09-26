package com.jeesite.API.service;

/**
 * 错误码表.
 * <p>
 * Created by xingfinal on 15/11/28.
 */
public enum Code {
    // 1024以内, 同HTTP
    SUCCESS(200, "Success."),
    API_PHONE_ERROR(903, "手机号授权失败"),
    API_USER_AUTH_ERROR(5000,"验证失败" ),
    API_QYQ_KC_NULL(5001,"券库存不足"),
    API_ORDER_ERROR(10001,"订单生成失败"),
    API_ORDER_PAY_ERROR(10002,"获取预支付订单号失败"),
    API_ORDER_ZDQXZF(10003,"订单已取消，无法支付"),
    API_COMPOSE_FAIL(10004, "合成失败"),
    API_CODE_GUOQI(10005,"验证码已过期."),
    API_CODE_ERROR(10006,"验证码错误."),
    API_REGISTER_DISTANCE(10007,"远程调用错误."),
    API_CODE_LOSR(10007,"二维码失效."),
    API_NULL_AUTH(100001,"权限不足"),
    API_NULL_ERROR(100002,"未知错误"),
    API_CTYD_YQR(100004,"商家已确认，无法取消"),
    API_SEND_SMS_ERROR(70002, "短信发送失败"),
    API_VERIFY_SMS_ERROR(70003, "短信校验失败"),
    API_YHQ_YLQ(70004, "不能重复领取"),
    API_VIP_CARD(70005, "请完善会员资料"),
    API_TXJE_NULL(80000, "金额不足，无法提现"),
    API_CHECK_NULL(80001, "入参不合法，有空值"),
    API_CHECK_KM(80002, "请勿重复上传卡券"),
    API_CHECK_COUNT(80003, "回收券已达到最大回收量"),
    API_PARENT_ONE(200, "邀请码无效，请前往[我的推广-绑定邀请]重新绑定"),
    API_SUCCESS_REGISTER(200, "注册成功"),
    ;

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
