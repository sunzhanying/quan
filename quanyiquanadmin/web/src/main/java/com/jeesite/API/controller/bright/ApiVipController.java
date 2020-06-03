package com.jeesite.API.controller.bright;

import com.alibaba.druid.sql.visitor.functions.If;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.service.VipCardPayService;
import com.jeesite.API.util.QRCodeUtil;
import com.jeesite.API.util.RandomUtils;
import com.jeesite.API.util.ZyUtil.SweepCodeService;
import com.jeesite.API.util.aliyun.SmsUtil;
import com.jeesite.API.zyapi.BaseResult;
import com.jeesite.API.zyapi.ZyAPI;
import com.jeesite.modules.bright.hyk.entity.VipCard;
import com.jeesite.modules.bright.hyk.service.VipCardService;
import com.jeesite.modules.bright.khvipcard.dao.KhVipcardDao;
import com.jeesite.modules.bright.khvipcard.entity.KhVipcard;
import com.jeesite.modules.bright.khvipcard.service.KhVipcardService;
import com.jeesite.modules.bright.sms.dao.SmsRecordDao;
import com.jeesite.modules.bright.sms.entity.SmsRecord;
import com.jeesite.modules.bright.sms.service.SmsRecordService;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.service.khxx.KhXxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(description = "关于vip的接口")
@RestController
@RequestMapping("${apiPath}/vip")
public class ApiVipController {

    @Autowired
    private VipCardService vipCardService;
    @Autowired
    private VipCardPayService vipCardPayService;
    @Autowired
    private KhVipcardService khVipcardService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private SmsRecordDao smsRecordDao;
    @Autowired
    private KhXxDao khXxDao;
    @Autowired
    private KhXxService khXxService;
    @Autowired
    private KhVipcardDao khVipcardDao;
    @Autowired
    private ZyAPI zyAPI;

    @ApiOperation(value = "createQrcode", notes = "创建会员卡二维码", httpMethod = "POST")
    @RequestMapping(value = "/createQrcode", method = RequestMethod.POST)
    public Map createQrcode(HttpServletResponse resp,HttpServletRequest request) throws Exception{
        Map<String,Object> map = new HashMap<>();
        //查询第三方会员余额
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        BaseResult baseResult = zyAPI.queryByNumber(khXx.getId());
        if (!"success".equals(baseResult.getStatus())){
            map.put("message", "第三方接口出现问题，无法显示二维码");
            map.put("code", false);
        }else {
            OutputStream out=resp.getOutputStream();
            ByteArrayOutputStream bt= new ByteArrayOutputStream();
            QRCodeUtil.encode(khXx.getId() ,null, bt,true);
            BASE64Encoder bs=new BASE64Encoder();
            map.put("qcode", bt.toByteArray());
            KhVipcard khVipcard = khVipcardDao.findByKhidByZt(khXx.getId(), KhVipcard.VIP_CARD_PAY_WC).get(0);
            //启动定时器
            SweepCodeService.setScheduler(khXx.getId(), khVipcard);
            map.put("img", khVipcard.getQimg());
            map.put("ye", khVipcard.getYe());
            map.put("code", true);
        }
        return map;
    }

    @ApiOperation(value = "isSweepCode", notes = "判断是否已经扫码成功", httpMethod = "POST")
    @RequestMapping(value = "/isSweepCode", method = RequestMethod.POST)
    public Object isSweepCode(HttpServletRequest request) throws Exception{
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        BaseResult baseResult = zyAPI.queryByNumber(khXx.getId());
        return baseResult;
    }

    @ApiOperation(value = "isbuy", notes = "判断用户是否已经购买会员卡", httpMethod = "POST")
    @RequestMapping(value = "/isbuy", method = RequestMethod.POST)
    public Response isbuy(HttpServletRequest request) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        if (khVipcardDao.countByKhidByZt(khXx.getId(), KhVipcard.VIP_CARD_PAY_WC) > 0){
            return new Response(true);
        }
        return new Response(false);
    }

    @ApiOperation(value = "getVipCardAll", notes = "获取所有会员卡", httpMethod = "GET")
    @RequestMapping(value = "/getVipCardAll", method = RequestMethod.GET)
    public Response getVipCardAll() {
        VipCard vipCard = new VipCard();
        vipCard.setSxj(VipCard.VIP_CARD_SJ);
        return new Response(vipCardService.findList(vipCard));
    }


    /*@ApiOperation(value = "buyVipCard", notes = "购买会员卡", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardid", value = "会员卡id", required = true)
    })
    @RequestMapping(value = "/buyVipCard", method = RequestMethod.POST)
    public Response buyVipCard(HttpServletRequest request, @RequestParam String cardid) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        if (khVipcardDao.countByKhidByZt(khXx.getId(), KhVipcard.VIP_CARD_PAY_WC) > 0){
            return new Response(Code.API_VIP_CARD_REPEAT);
        }

        if (Strings.isNullOrEmpty(khXx.getSj()) || Strings.isNullOrEmpty(khXx.getXm())){
            return new Response(Code.API_VIP_CARD);
        }
        return new Response(vipCardPayService.pay(request, khXx, cardid));
    }*/

    //购买会员卡回调
    @RequestMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException,ClientException {
        vipCardPayService.payNotify(request, response);
    }

    @ApiOperation(value = "vipCardList", notes = "用户会员卡列表", httpMethod = "GET")
    @RequestMapping(value = "/vipCard", method = RequestMethod.GET)
    public Response vipCard(HttpServletRequest request, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        PageHelper.startPage(page,size);
        return new Response(new PageInfo<>(khVipcardService.findByKhidByZt(khXx.getId(), KhVipcard.VIP_CARD_PAY_WC)));
    }


    @ApiOperation(value = "khVipCard", notes = "用户会员卡详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "khVipCardid", value = "用户会员卡id", required = true)
    })
    @RequestMapping(value = "/khVipCard", method = RequestMethod.GET)
    public Response khVipCard(@RequestParam String khVipCardid) {
        return new Response(khVipcardService.get(khVipCardid));
    }

    //发送验证码
    @ApiOperation(value = "send-register-sms",notes = "发送验证码",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone",value = "手机号",required = true)
    })
    @RequestMapping(value = "/send-register-sms",method = RequestMethod.POST)
    public Response<String> sendRegisterSms(@RequestParam String phone) throws ClientException {
        if (khXxDao.countByPhone(phone) > 0){
            return new Response<>(Code.API_SEND_SMS_ERROR, "该手机号已绑定用户");
        }
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setPhone(phone);
        smsRecord.setZt(SmsRecord.PHONE_ZT_DYZ);
        SmsRecord lastRecord = smsRecordService.getSmsRecord(smsRecord);
        // 1分钟之内, 不允许发送多条
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -1);// 1分钟之前的时间
        Date beforeD = beforeTime.getTime();

        if (lastRecord != null && beforeD.compareTo(lastRecord.getSendTime())!=1) {
            return new Response<>(Code.API_SEND_SMS_ERROR, "短信发送过于频繁");
        }

        String data;
        String verifyCode = RandomUtils.generateSmsVerifyCode();
        Map map=new HashMap();
        map.put("code",verifyCode);
        //阿里
        SendSmsResponse result = SmsUtil.sendSms(SmsUtil.SMS_CODE_PHONE, phone, map);

        if (result != null && "OK".equals(result.getCode())) {
            // 将旧的验证码作废
            smsRecordDao.cancelOldVerifyCode(phone);

            smsRecord.setPlatformNo(result.getBizId());
            smsRecord.setPhone(phone);
            smsRecord.setCode(verifyCode);
            smsRecord.setSendTime(new Date());

            smsRecordService.save(smsRecord);
            return new Response<>(Code.SUCCESS);
        }else{
            data = result.getMessage();
        }

        return new Response<>(Code.API_SEND_SMS_ERROR, data);
    }

    //验证手机号
    @ApiOperation(value = "verify-register-sms",notes = "验证手机号",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "姓名",required = true),
            @ApiImplicitParam(name="phone",value = "手机号",required = true),
            @ApiImplicitParam(name="code",value = "验证码",required = false)
    })
    @RequestMapping(value = "/verify-register-sms",method = RequestMethod.POST)
    public Response verifySms(HttpServletRequest request, @RequestParam String name, @RequestParam String phone, String code) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        if (!Strings.isNullOrEmpty(code)){
            SmsRecord record = new SmsRecord();
            record.setPhone(phone);
            record.setZt(SmsRecord.PHONE_ZT_DYZ);
            record = smsRecordService.getSmsRecord(record);
            if (record == null) {
                return new Response<>(Code.API_VERIFY_SMS_ERROR, "验证码已过期.");
            }

            if (!code.equals(record.getCode())) {
                return new Response<>(Code.API_VERIFY_SMS_ERROR, "验证码不正确.");
            }

            record.setZt(SmsRecord.PHONE_ZT_YYZ);
            record.setVerifyTime(new Date());
            smsRecordDao.update(record);
        }
        //更新用户
        khXx.setXm(name);
        khXx.setSj(phone);
        khXxService.update(khXx);
        return new Response(Code.SUCCESS);
    }
}
