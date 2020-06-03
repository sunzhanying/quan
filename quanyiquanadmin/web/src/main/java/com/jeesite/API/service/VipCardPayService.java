
package com.jeesite.API.service;


import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.common.base.Strings;
import com.jeesite.API.util.aliyun.SmsUtil;
import com.jeesite.API.weixin.api.PayMchAPI;
import com.jeesite.API.weixin.bean.paymch.MchBaseResult;
import com.jeesite.API.weixin.bean.paymch.MchPayNotify;
import com.jeesite.API.weixin.bean.paymch.Unifiedorder;
import com.jeesite.API.weixin.bean.paymch.UnifiedorderResult;
import com.jeesite.API.weixin.support.ExpireKey;
import com.jeesite.API.weixin.support.expirekey.DefaultExpireKey;
import com.jeesite.API.weixin.util.*;
import com.jeesite.API.zyapi.BaseMemberYeResult;
import com.jeesite.API.zyapi.ZyAPI;
import com.jeesite.modules.bright.formid.entity.FormId;
import com.jeesite.modules.bright.formid.service.FormIdService;
import com.jeesite.modules.bright.hyk.entity.VipCard;
import com.jeesite.modules.bright.hyk.service.VipCardService;
import com.jeesite.modules.bright.khvipcard.entity.KhVipcard;
import com.jeesite.modules.bright.khvipcard.service.KhVipcardService;
import com.jeesite.modules.bright.points.service.pointsconfig.PointsConfigService;
import com.jeesite.modules.bright.points.service.pointslog.PointsLogService;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.service.khxx.KhXxService;
import com.jeesite.modules.bright.util.KhXwUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 支付Service
 *
 * @author 李金辉
 * @version 2018-05-24
 */

@Service
@Transactional(readOnly = true)
public class VipCardPayService {
    @Value("${weixin.appid}")
    private String wxAppId;

    @Value("${weixin.Mch_id}")
    private String Mch_id;

    @Value("${weixin.Notify_vip_card_url}")
    private String Notify_url;

    @Value("${weixin.Mch_key}")
    private String key;

    @Autowired
    private FormIdService formIdService;
    @Autowired
    private VipCardService vipCardService;
    @Autowired
    private KhVipcardService khVipcardService;
    @Autowired
    private PointsLogService pointsLogService;
    @Autowired
    private KhXxService khXxService;
    @Autowired
    private PointsConfigService pointsConfigService;
    @Autowired
    private ZyAPI zyAPI;

    String product_id = "";
    String body = "";


    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    private final Log log = LogFactory.getLog(getClass());

    //微信支付
    @Transactional(readOnly = false)
    public Object pay(HttpServletRequest request, KhXx khXx, String cardid) {

        Double total_fee = 0D;
        String orderid = "";
        String attach = "";

        //查询会员卡
        VipCard vipCard = vipCardService.get(cardid);
        if (vipCard != null){
            if (vipCard.getJe() > 0){
                total_fee = vipCard.getJe();
                KhVipcard khVipcard = new KhVipcard();
                khVipcard.setKhid(khXx.getId());
                khVipcard.setVipCardId(cardid);
                khVipcard.setCardName(vipCard.getName());
                khVipcard.setCardFname(vipCard.getFname());
                khVipcard.setJe(vipCard.getJe());
                khVipcard.setYe(vipCard.getJe()+Double.valueOf(vipCard.getZje()));
                khVipcard.setZt(KhVipcard.VIP_CARD_PAY_DF);
                khVipcard.setImg(vipCard.getImg());
                khVipcard.setQimg(vipCard.getQimg());
                khVipcard.setDimg(vipCard.getDimg());
                khVipcard.setMimg(vipCard.getMimg());
                khVipcard.setZje(Double.valueOf(vipCard.getZje()));
                //添加用户会员卡
                khVipcardService.save(khVipcard);
                body = vipCard.getName();
                attach = String.valueOf(khXx.getCrmid());
                orderid = khVipcard.getId();
                //添加客户行为
                KhXwUtil.addXw("19", khXx.getId(), attach, khVipcard.getId());
            }
        }

        log.info("》》》》》》》》》》"+total_fee);
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setProduct_id(product_id);
        unifiedorder.setBody(body);
        unifiedorder.setAttach(attach);
        unifiedorder.setTotal_fee(String.valueOf((int)(total_fee * 100)));//单位分
        unifiedorder.setAppid(wxAppId);
        unifiedorder.setMch_id(Mch_id);
        unifiedorder.setGoods_tag("APIUYHGBJG");
        unifiedorder.setOpenid(khXx.getOpenId());
        unifiedorder.setNonce_str(IdGen.wxRandom(32));
        unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
        unifiedorder.setOut_trade_no(orderid);
        unifiedorder.setNotify_url(Notify_url);
        unifiedorder.setTrade_type("JSAPI");
        log.info(unifiedorder);
        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
        log.info(unifiedorderResult);
        log.info("获取与支付订单号回执：" + unifiedorderResult.getReturn_msg());
        if ("SUCCESS".equals(unifiedorderResult.getResult_code()) && "SUCCESS".equals(unifiedorderResult.getReturn_code())) {
            FormId form=new FormId();
            form.setFormId(unifiedorderResult.getPrepay_id());
            form.setOpenId(khXx.getOpenId());
            form.setTimes(3);
            formIdService.save(form);

            List list1=new ArrayList();
            list1.add(PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(),wxAppId,key));
            list1.add(orderid);
            return new Response(list1);

            // return new Response(PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(), wxAppId, key));
        } else {
            return new Response("获取预支付订单号失败");
        }
    }


    @Transactional(readOnly = false)
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws IOException,ClientException {
        //获取请求数据
        String xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
        //将XML转为MAP,确保所有字段都参与签名验证
        Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
        //转换数据对象
        MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xmlData);
        //已处理 去重
        if (payNotify == null || expireKey.exists(payNotify.getTransaction_id())) {
            return;
        } else {
            //签名验证
            if (SignatureUtil.validateSign(mapData, key)) {
                expireKey.add(payNotify.getTransaction_id());
                KhVipcard khVipcard = khVipcardService.get(payNotify.getOut_trade_no());

                System.out.println("回调数据："+payNotify.toString());
                //System.out.println("回调持仓："+payNotify.getFee_type());

                //未支付成功时走业务
                if (KhVipcard.VIP_CARD_PAY_DF.equals(khVipcard.getZt())){
                    //增加积分
                    /*Long jf = (long) Math.floor(Double.valueOf(payNotify.getTotal_fee())/100);
                    if (jf > 0){
                        KhXx khXx = khXxService.get(khVipcard.getKhid());
                        PointsConfig pointsConfig = pointsConfigService.get(PointsConfig.POINTSCONFIG_PAY);
                        pointsLogService.savePoints(khXx, pointsConfig, jf);
                    }*/
                    //更新支付状态
                    khVipcard.setZt(KhVipcard.VIP_CARD_PAY_WC);
                    khVipcardService.save(khVipcard);
                    //修改第三方会员的余额
                    try {
                        BaseMemberYeResult result = zyAPI.updateBalancePointByIncrement(payNotify.getAttach(), khVipcard.getYe(), khVipcard.getYe());
                        System.out.println(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                MchBaseResult baseResult = new MchBaseResult();
                baseResult.setReturn_code("SUCCESS");
                baseResult.setReturn_msg("OK");
                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            } else {
                MchBaseResult baseResult = new MchBaseResult();
                baseResult.setReturn_code("FAIL");
                baseResult.setReturn_msg("ERROR");
                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            }
        }
    }

}
