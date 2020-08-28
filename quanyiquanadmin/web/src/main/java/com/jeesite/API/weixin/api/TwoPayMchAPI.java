package com.jeesite.API.weixin.api;


import com.jeesite.API.weixin.bean.paymch.Transfers;
import com.jeesite.API.weixin.bean.paymch.TransfersResult;
import com.jeesite.API.weixin.client.TwoLocalHttpClient;
import com.jeesite.API.weixin.util.MapUtil;
import com.jeesite.API.weixin.util.SignatureUtil;
import com.jeesite.API.weixin.util.XMLConverUtil;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 微信支付 基于V3.X 版本
 *
 * @author Yi
 */
public class TwoPayMchAPI extends BaseAPI {


    /**
     * 企业付款
     *
     * @param transfers
     * @param key
     * @return
     */
    public static TransfersResult mmpaymkttransfersPromotionTransfers(Transfers transfers, String key) {
        Map<String, String> map = MapUtil.objectToMap(transfers);
        String sign = SignatureUtil.generateSign(map, key);
        transfers.setSign(sign);
        String secapiPayRefundXML = XMLConverUtil.convertToXML(transfers);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(xmlHeader)
                .setUri(MCH_URI + "/mmpaymkttransfers/promotion/transfers")
                .setEntity(new StringEntity(secapiPayRefundXML, Charset.forName("utf-8")))
                .build();
        return TwoLocalHttpClient.keyStoreExecuteXmlResult(transfers.getMchid(), httpUriRequest, TransfersResult.class);
    }

}
