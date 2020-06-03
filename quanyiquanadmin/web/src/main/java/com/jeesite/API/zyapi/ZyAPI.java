package com.jeesite.API.zyapi;



import com.alibaba.fastjson.JSON;
import com.jeesite.API.weixin.bean.sns.SnsToken;

import com.jeesite.API.weixin.client.LocalHttpClient;
import com.jeesite.common.lang.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 智廷开放平台
 *
 * @author 马晓亮
 */
@Component
public class ZyAPI extends BaseAPI {


    @Value("${zyapi.appId}")
    private String zyAppId;
    @Value("${zyapi.appKey}")
    private String zyAppKey;

    @Autowired
    private Md5String md5String;

    HttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * 添加会员
     * @param map
     * @return
     * @throws Exception
     */
    public BaseResult addMember(MemberResult m) throws  Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("appId", zyAppId);
        map.put("customerInfo", m);

        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/customerOpenApi/add")
                .addHeader(jsonHeader)
                .addHeader(userAgentHeader)
                .addHeader(acceptHeader)
                .addHeader(timeStampHeader)
                .addHeader("data-signature", md5String.Md5(JSON.toJSONString(map), zyAppKey))
                .setEntity(new StringEntity(JSON.toJSONString(map), "utf-8"))
                .build();
        HttpResponse response = httpClient.execute(httpUriRequest);

        return JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"),BaseResult.class);
    }

    /**
     * 根据会员号查询会员
     * @param number 会员号
     * @return
     * @throws Exception
     */
    public BaseResult queryByNumber(String number) throws  Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("appId", zyAppId);
        map.put("customerNum", number);

        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/customerOpenApi/queryByNumber")
                .addHeader(jsonHeader)
                .addHeader(userAgentHeader)
                .addHeader(acceptHeader)
                .addHeader(timeStampHeader)
                .addHeader("data-signature", md5String.Md5(JSON.toJSONString(map), zyAppKey))
                .setEntity(new StringEntity(JSON.toJSONString(map), "utf-8"))
                .build();
        HttpResponse response = httpClient.execute(httpUriRequest);

        return (BaseResult)JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"),BaseResult.class);
    }

    /**
     * 根据标识查询会员
     * @return
     * @throws Exception
     */
    public BaseResult queryByUid(String customerUid) throws  Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("appId", zyAppId);
        map.put("customerUid", customerUid);

        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/customerOpenApi/queryByUid")
                .addHeader(jsonHeader)
                .addHeader(userAgentHeader)
                .addHeader(acceptHeader)
                .addHeader(timeStampHeader)
                .addHeader("data-signature", md5String.Md5(JSON.toJSONString(map), zyAppKey))
                .setEntity(new StringEntity(JSON.toJSONString(map), "utf-8"))
                .build();
        HttpResponse response = httpClient.execute(httpUriRequest);

        return JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"),BaseResult.class);
    }


    /**
     * 修改金额积分返回
     * @param customerUid  标识id
     * @param balanceIncrement 增加金额
     * @param pointIncrement  增加积分
     * @return
     * @throws Exception
     */
    public BaseMemberYeResult updateBalancePointByIncrement(String customerUid, Double balanceIncrement, Double pointIncrement) throws  Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("appId", zyAppId);
        map.put("customerUid", customerUid);
        map.put("balanceIncrement", balanceIncrement);
        map.put("pointIncrement", pointIncrement);
        map.put("dataChangeTime", DateUtils.formatDateTime(new Date()));

        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/customerOpenApi/updateBalancePointByIncrement")
                .addHeader(jsonHeader)
                .addHeader(userAgentHeader)
                .addHeader(acceptHeader)
                .addHeader(timeStampHeader)
                .addHeader("data-signature", md5String.Md5(JSON.toJSONString(map), zyAppKey))
                .setEntity(new StringEntity(JSON.toJSONString(map), "utf-8"))
                .build();
        HttpResponse response = httpClient.execute(httpUriRequest);

        return JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"),BaseMemberYeResult.class);
    }

}
