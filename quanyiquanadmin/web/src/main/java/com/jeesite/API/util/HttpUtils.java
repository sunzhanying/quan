package com.jeesite.API.util;


import com.jeesite.API.weixin.client.LocalHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author Shixing
 */
public class HttpUtils {

    public static String httpRequest(HttpUriRequest httpUriRequest) {
        CloseableHttpResponse response = null;
        String responseContent = null; // 响应内容
        try {
            response = LocalHttpClient.execute(httpUriRequest);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseContent;
    }
}
