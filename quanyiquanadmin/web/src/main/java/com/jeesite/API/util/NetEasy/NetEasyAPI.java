package com.jeesite.API.util.NetEasy;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.API.util.CheckSumBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NetEasyAPI {



    @Value("${netEase.appKey}")
    String appKey;
    @Value("${netEase.appSecret}")
    String appSecret;
    HttpClient httpClient = HttpClientBuilder.create().build();
    /**
     * 创建accid
     * @return
     * @throws IOException
     */
    public BasicResult<RegisterResult> creatAccid() throws IOException {
        HttpPost httpPost =initHttp("https://api.netease.im/nimserver/user/create.action");
                // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", IdGen.uuid()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);


      return   JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"),new TypeReference<BasicResult<RegisterResult>>(){});
      // return (BasicResult) JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8").replaceAll(":","="),BasicResult.class);
    }


    /**
     * 创建聊天室
     * @return
     * @throws IOException
     */
    public BasicResult<RegisterResult> creatChat() throws IOException {
        HttpPost httpPost =initHttp("https://api.netease.im/nimserver/chatroom/create.action");
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //聊天室创建者
        nvps.add(new BasicNameValuePair("creator", IdGen.uuid()));
        //聊天室名称
        nvps.add(new BasicNameValuePair("name", IdGen.uuid()));
        nvps.add(new BasicNameValuePair("accid", IdGen.uuid()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        return (BasicResult<RegisterResult>) JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"),BasicResult.class);
    }
    /**
     * 创建直播
     * @return
     * @throws IOException
     */
    public BasicResult<RegisterResult> creatLive() throws IOException {
        //https://vcloud.163.com/app/channel/create
        HttpPost httpPost =initHttp("https://api.netease.im/nimserver/chatroom/create.action");
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //直播频道名称
        nvps.add(new BasicNameValuePair("name", IdGen.uuid()));
        //直播频道类型
        nvps.add(new BasicNameValuePair("type", IdGen.uuid()));
        nvps.add(new BasicNameValuePair("accid", IdGen.uuid()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        return (BasicResult<RegisterResult>) JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"),BasicResult.class);
    }



    public HttpPost initHttp(String url){
        HttpPost httpPost = new HttpPost(url);
        String nonce =  "12345";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return httpPost;
    }

    public static void main(String[] args) {
        BasicResult<RegisterResult> b= JSON.parseObject("{\"code\":200,\"info\":{\"token\":\"8552702f2ebc0c0b314ea48cd56df813\",\"accid\":\"dbe74a7521d242d4aeaa9e7f6f86a280\",\"name\":\"\"}}",new TypeReference<BasicResult<RegisterResult>>(){});
        System.out.println(b.getInfo().getAccid());
    }
}