package com.jeesite.API.weixin.api;


import com.jeesite.API.weixin.client.LocalHttpClient;
import com.jeesite.API.weixin.bean.token.Token;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

public class TokenAPI extends BaseAPI {

    /**
     * 获取access_token
     *
     * @param appid
     * @param secret
     * @return
     */
    public static Token token(String appid, String secret) {
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri(BASE_URI + "/cgi-bin/token")
                .addParameter("grant_type", "client_credential")
                .addParameter("appid", appid)
                .addParameter("secret", secret)
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, Token.class);
    }

}
