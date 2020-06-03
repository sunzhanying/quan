package com.jeesite.API.util;

/**
 * Created by xingfinal on 16/7/14.
 */
public class RandomUtils {

    /**
     * 生成6位短信验证码
     * @return
     */
    public static String generateSmsVerifyCode() {
        long now = System.currentTimeMillis();

        String code = "";
        for (int i = 0; i < 6; ++i) {
            code += now % 10;
            now >>= 1;
        }

        return code;
    }
}
