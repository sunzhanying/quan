package com.jeesite.API.zyapi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * 生成MD5
 *
 */
@Component
public class Md5String {


    public  String Md5(String JSONStr, String appKey) throws Exception{
       return encryptToMd5String(JSONStr,appKey);
    }

    public String encryptToMd5String(String content,String appKey) throws Exception
    {
        return encryptToMd5String(
                  StringUtils.trim (appKey)+StringUtils.trim(content));
    }

    public String encryptToMd5String(String content) throws Exception
    {
        String md5String = null;
        MessageDigest md = MessageDigest.getInstance("md5");
        md.update(content.getBytes("UTF-8"));
        md5String = parseByte2HexString(md.digest());
        return md5String;
    }

    public String parseByte2HexString(byte buf[])
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < buf.length; i++)
        {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {hex = '0' + hex;}
            stringBuffer.append(hex.toUpperCase());
        }
        return stringBuffer.toString();
    }


}
