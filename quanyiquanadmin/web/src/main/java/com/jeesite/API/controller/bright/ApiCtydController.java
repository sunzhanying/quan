package com.jeesite.API.controller.bright;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.util.RandomUtils;
import com.jeesite.API.util.aliyun.SmsUtil;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.bright.ctyd.entity.Ctyd;
import com.jeesite.modules.bright.ctyd.service.CtydService;
import com.jeesite.modules.bright.sms.dao.SmsRecordDao;
import com.jeesite.modules.bright.sms.entity.SmsRecord;
import com.jeesite.modules.bright.sms.service.SmsRecordService;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(description = "餐厅预订接口")
@RestController
@RequestMapping("${apiPath}/ctyd")
public class ApiCtydController {

    @Value("${admin_phone}")
    private String adminPhone;

    @Autowired
    private CtydService ctydService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private SmsRecordDao smsRecordDao;


    @ApiOperation(value = "saveCtyd",notes = "保存餐厅预订",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="rs",value = "预订人数",required = true),
            @ApiImplicitParam(name="ydDate",value = "预订日期",required = true),
            @ApiImplicitParam(name="ydTime",value = "预订时间",required = true),
            @ApiImplicitParam(name="yddf",value = "预订地方 大厅 包间",required = true),
            @ApiImplicitParam(name="name",value = "姓名",required = true),
            @ApiImplicitParam(name="sex",value = "性别1男2女",required = true),
            @ApiImplicitParam(name="phone",value = "电话",required = true),
            @ApiImplicitParam(name="remarks",value = "备注信息",required = false),
            @ApiImplicitParam(name="code",value = "验证码",required = true)
    })
    @RequestMapping(value = "/saveCtyd",method = RequestMethod.POST)
    public Response saveCtyd(HttpServletRequest request, @RequestParam long rs, @RequestParam String ydDate,
                             @RequestParam String ydTime, @RequestParam String yddf, @RequestParam String name, @RequestParam int sex,
                             @RequestParam String phone, String remarks, @RequestParam String code)  throws ClientException{
        //验证手机号
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


        KhXx khXx = (KhXx) request.getAttribute("khXx");
        Ctyd ctyd = new Ctyd();
        ctyd.setKhid(khXx.getId());
        ctyd.setRs(rs);
        ctyd.setYdDate(DateUtils.parseDate(ydDate));
        ctyd.setYdTime(DateUtils.parseDate(ydTime));
        ctyd.setYddf(yddf);
        ctyd.setName(name);
        ctyd.setSex(sex);
        ctyd.setPhone(phone);
        ctyd.setRemarks(remarks);
        ctyd.setZt(Ctyd.CTYD_ZT_YDZ);
        ctydService.save(ctyd);
        // 向管理员发送 预订信息
        Map map=new HashMap();
        map.put("name", name);
        map.put("phone", phone);
        map.put("date", ydTime);
        //阿里
        try {
            String[] str = adminPhone.split(",");
            for (String s:str){
                SendSmsResponse result = SmsUtil.sendSms(SmsUtil.SMS_CODE_CTYD, s, map);
                System.out.println(result.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Response(ctyd.getId());
    }

    @ApiOperation(value = "getCtyd",notes = "获取餐厅预订详情",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "预订id",required = true)
    })
    @RequestMapping(value = "/getCtyd",method = RequestMethod.GET)
    public Response getCtyd(@RequestParam String id) {
        return new Response(ctydService.get(id));
    }

    @ApiOperation(value = "qxCtyd",notes = "取消餐厅预订",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "预订id",required = true)
    })
    @RequestMapping(value = "/qxCtyd",method = RequestMethod.GET)
    public Response qxCtyd(@RequestParam String id) {
        Ctyd ctyd = ctydService.get(id);
        if (ctyd.getZt() >= Ctyd.CTYD_ZT_YQR){
            return new Response(Code.API_CTYD_YQR);
        }
        return new Response(ctydService.updateZt(id, Ctyd.CTYD_ZT_YQX));
    }


    @ApiOperation(value = "ctydList",notes = "餐厅预订列表",httpMethod ="GET")
    @RequestMapping(value = "/ctydList",method = RequestMethod.GET)
    public Response ctydList(HttpServletRequest request, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                             @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        PageHelper.startPage(page,size);
        Ctyd ctyd = new Ctyd();
        ctyd.setKhid(khXx.getId());
        return new Response(new PageInfo<>(ctydService.findList(ctyd)));
    }

    //发送验证码
    @ApiOperation(value = "send-register-sms",notes = "发送验证码",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone",value = "手机号",required = true)
    })
    @RequestMapping(value = "/send-register-sms",method = RequestMethod.POST)
    public Response<String> sendRegisterSms(@RequestParam String phone) throws ClientException {
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
            @ApiImplicitParam(name="phone",value = "手机号",required = true),
            @ApiImplicitParam(name="code",value = "验证码",required = true)
    })
    @RequestMapping(value = "/verify-register-sms",method = RequestMethod.POST)
    public Response verifySms(@RequestParam String phone, @RequestParam String code) {
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
        return new Response(Code.SUCCESS);
    }
}
