package com.jeesite.API.util;

import java.util.Random;

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

    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++){
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /*public static void main(String[] args) throws Exception {
        String ss = getRandomString(6);
        System.out.println(ss);
    }*/
}
