package com.jeesite.API.controller.bright;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.util.RandomUtils;
import com.jeesite.API.util.RedisTemplateUtils;
import com.jeesite.API.util.aliyun.SmsUtil;
import com.jeesite.API.weixin.api.SnsAPI;
import com.jeesite.API.weixin.api.TokenAPI;
import com.jeesite.API.weixin.api.UnlimitAPI;
import com.jeesite.API.weixin.bean.sns.SnsToken;
import com.jeesite.API.weixin.bean.token.Token;
import com.jeesite.API.weixin.bean.user.Phone;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.khvipcard.dao.KhVipcardDao;
import com.jeesite.modules.bright.khyhq.dao.KhYhqDao;
import com.jeesite.modules.bright.sms.dao.SmsRecordDao;
import com.jeesite.modules.bright.sms.entity.SmsRecord;
import com.jeesite.modules.bright.sms.service.SmsRecordService;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.service.khxx.KhXxService;
import com.jeesite.modules.bright.util.AES;
import com.jeesite.modules.bright.util.WxPKCS7Encoder;
import com.jeesite.modules.collect.entity.Collect;
import com.jeesite.modules.collect.service.CollectService;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.order.service.OrderService;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyhsmx.service.QyhsMxService;
import com.jeesite.modules.qyjg.entity.Qyjg;
import com.jeesite.modules.qyjg.service.QyjgService;
import com.jeesite.modules.txsh.entity.Sell;
import com.jeesite.modules.txsh.entity.Txsh;
import com.jeesite.modules.txsh.service.SellService;
import com.jeesite.modules.txsh.service.TxshService;
import com.jeesite.utils.Paper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 客户操作
 */

@Api(description = "客户信息相关接口")
@RestController
@RequestMapping("${apiPath}/khxx")
public class ApiKhxxController {
    private final Log log = LogFactory.getLog(getClass());


    @Value("${weixin.appid}")
    private String wxAppId;
    @Value("${weixin.appsecret}")
    private String wxAppSecret;

    //买方
    @Value("${weixin.appidA}")
    private String wxAppIdBuyer;
    @Value("${weixin.appsecretA}")
    private String wxAppSecretBuyer;

    @Value("${file.baseDir}")
    private String baseDir;

    @Autowired
    private KhXxService khXxService;
    @Autowired
    private KhYhqDao khYhqDao;
    @Value("${spring.redis.goods_prefix}")
    protected String goodsPrefix;
    @Autowired
    private KhVipcardDao khVipcardDao;

    @Autowired
    private RedisTemplateUtils redisUtils;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private SmsRecordDao smsRecordDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private QyhsMxService qyhsMxService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private QyjgService qyjgService;
    @Autowired
    private TxshService txshService;
    @Autowired
    private QyhsMxDao qyhsMxDao;
    @Autowired
    private KhXxDao khXxDao;
    @Autowired
    private SellService sellService;

     /**
     * 微信用户登录
     *
     * @return 授权结果，返回包含JWT的字符串
     */
    @ApiOperation(value = "保存微信头像昵称",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="avatar",value = "头像",required = true),
            @ApiImplicitParam(name="nickname",value = "昵称",required = true)
    })
    @RequestMapping(value = "/saveAvatarAndNickname",method = RequestMethod.POST)
    public Response login(@RequestParam String avatar,
                          @RequestParam String nickname, HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        khXx.setWxtx(avatar);
        khXx.setWxnc(nickname);
        khXxService.save(khXx);
        return new Response(Code.SUCCESS);
    }


    /**
     * 微信用户获取手机号
     *
     * @return 授权结果，返回包含JWT的字符串
     */
    @ApiOperation(value = "微信用户获取手机号",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="encryptedData",value = "",required = true),
            @ApiImplicitParam(name="iv",value = "",required = true),
            @ApiImplicitParam(name="code",value = "",required = true)
    })
    @RequestMapping(value = "/addPhone",method = RequestMethod.POST)
    public Response getPhone(HttpServletRequest request, @RequestParam String encryptedData, @RequestParam String iv, @RequestParam String code) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        try {
            SnsToken token = SnsAPI.oauth2AccessToken(wxAppId, wxAppSecret, code);
            if (encryptedData != null && iv != null && !"".equals(token.getSession_Key())
                    && token.getSession_Key() != null) {
                Phone phone1 = decrypt(token.getSession_Key(), encryptedData, iv);
                khXx.setSj(phone1.getPhoneNumber());
                khXxService.update(khXx);
                return new Response(Code.SUCCESS);
            }
        } catch(Exception e){
            return new Response(Code.API_PHONE_ERROR);
        }
        return new Response(Code.API_PHONE_ERROR);
    }

    /**
     * 解密数据
     * @return
     * @throws Exception
     */
    /*@RequestMapping(value = "/getPhone",method = RequestMethod.POST)*/
    public static Phone decrypt(String seesionkey, String encryptedData, String iv){
        System.out.println("加密数据===========》"+encryptedData);
        System.out.println("vi===========》"+iv);
        String result = "";
        try {
            byte[] resultByte = AES.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(seesionkey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));
                //JSONObject jsonObject = JSONObject.fromObject(result);
                //String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
                /*if(!appId.equals(decryptAppid)){
                    result = "";
                }*/
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        System.out.println(result);
        Phone phone = JSON.parseObject(result, Phone.class);
        System.out.println("=============="+result);
        System.out.println("======手机号========"+phone.getPhoneNumber());
        return phone;
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息",httpMethod ="GET")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public Response userInfo(HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        return new Response(khXx);
    }

    @ApiOperation(value = "卖方端显示待审核 出售中 已售出 无效券",httpMethod ="GET")
    @RequestMapping(value = "/qyqZt",method = RequestMethod.GET)
    public Response qyqZt(HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Map<String,Object> map = new HashMap<>();
        QyhsMx qyhsMx = new QyhsMx();
        qyhsMx.setKhid(khXx.getId());
        //待审核
        qyhsMx.setZt(QyhsMx.STATUS_DSH);
        map.put("dsh", qyhsMxService.findCount(qyhsMx));
        //出售中
        qyhsMx.setZt(QyhsMx.STATUS_CSZ);
        map.put("csz", qyhsMxService.findCount(qyhsMx));
        //已售出
        qyhsMx.setZt(QyhsMx.STATUS_YFK);
        map.put("ysc", qyhsMxService.findCount(qyhsMx));
        //无效券
        qyhsMx.setZt(QyhsMx.STATUS_TH);
        map.put("wxq", qyhsMxService.findCount(qyhsMx));
        //已到账收益
        /*qyhsMx.setJszt(QyhsMx.STATUS_JS_YJS);
        String extColumn = "SUM(a.sy) AS \"sum\"";
        qyhsMx.getSqlMap().add("extColumn", extColumn);
        QyhsMx qyhsMx2 = qyhsMxDao.getByEntity(qyhsMx);
        if (qyhsMx2 != null){
            map.put("ydz", qyhsMx2.getSum());
        }else {
            map.put("ydz", 0.0);
        }*/

        //已到账新方法
        Double ydzMoney = txshService.findYdz(khXx.getId(),Txsh.TX_STATUS_TG);
        if (ydzMoney != null){
            /*String s1 = String.format("%.2f", ydzMoney);
            map.put("ydz", s1);*/
            map.put("ydz", ydzMoney);
        }else {
            map.put("ydz", 0.0);
        }

        //可提现收益
        /*QyhsMx qyhsMx1 = new QyhsMx();
        qyhsMx1.setKhid(khXx.getId());
        qyhsMx1.getSqlMap().getWhere().and("jszt", QueryType.LT, QyhsMx.STATUS_JS_YJS);// LT 小于的意思
        String extColumn1 = "SUM(a.sy) AS \"sum\"";
        qyhsMx1.getSqlMap().add("extColumn", extColumn1);
        QyhsMx qyhsMx3 = qyhsMxDao.getByEntity(qyhsMx1);
        if (qyhsMx3 != null){
            map.put("ktx", qyhsMx3.getSum());
        }else {
            map.put("ktx", 0.0);
        }*/
        //已到账新方法
        Double ktxMoney = txshService.findYdz(khXx.getId(),Txsh.TX_STATUS_SQZ);
        if (ydzMoney != null){
            map.put("ktx", ktxMoney);
        }else {
            map.put("ktx", 0.0);
        }

        return new Response(map);
    }

    @ApiOperation(value = "买方端显示提交投诉 待付款 已付款 纠纷中",httpMethod ="GET")
    @RequestMapping(value = "/orderZt",method = RequestMethod.GET)
    public Response orderZt(HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Map<String,Object> map = new HashMap<>();
        Order order = new Order();
        order.setUserId(khXx.getId());
        //提交投诉
        map.put("tjts", 0);
        //待付款
        order.setZt(Order.PAY_STATUS_DZF);
        map.put("dfk", orderService.findCount(order));
        //已付款
        order.setZt(Order.PAY_STATUS_YZF);
        map.put("yfk", orderService.findCount(order));
        //纠纷中
        map.put("jfz", 0);
        return new Response(map);
    }

    @ApiOperation(value = "收藏权益",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="spId",value = "",required = true),
    })
    @RequestMapping(value = "/collect",method = RequestMethod.GET)
    public Response collect(HttpServletRequest request, @RequestParam String spId) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Collect collect = new Collect();
        collect.setKhid(khXx.getId());
        collect.setSpId(spId);
        List<Collect> collectList = collectService.findList(collect);
        //取消收藏
        if (collectList.size() > 0){
            collectService.delete(collectList.get(0));
        }else { //添加收藏
            collect.preInsert();
            collectService.insert(collect);
        }
        return new Response(Code.SUCCESS);
    }

    @ApiOperation(value = "我的收藏",httpMethod ="GET")
    @RequestMapping(value = "/myCollect",method = RequestMethod.GET)
    public Page<Collect> myCollect(HttpServletRequest request,
                          @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                          @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Page<Collect> collectPage = new Page<>();
        collectPage.setPageNo(page);
        collectPage.setPageSize(size);
        Collect collect = new Collect();
        collect.setKhid(khXx.getId());
        collect.setPage(collectPage);
        List<Collect> collectList = collectService.findList(collect);
        Qyjg qyjg = new Qyjg();
        QyhsMx qyhsMx = new QyhsMx();
        collectList.forEach(item ->{
            //价格
            qyjg.setQyqId(item.getSpXx().getId());
            qyjg.setPageSize(1);
            item.getSpXx().setQyjg(qyjgService.findList(qyjg).get(0));
            //成交量
            qyhsMx.setQyqId(item.getSpXx().getId());
            qyhsMx.getSqlMap().getWhere().and("zt", QueryType.GT, QyhsMx.STATUS_DFK);
            item.getSpXx().setCjl(qyhsMxService.findCount(qyhsMx));
        });
        return collectPage.setList(collectList);
    }

    @ApiOperation(value = "保存用户姓名，手机号",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "姓名",required = true),
            @ApiImplicitParam(name="phone",value = "手机号",required = true),
            @ApiImplicitParam(name="code",value = "验证码",required = true),
            @ApiImplicitParam(name="inviteCode",value = "邀请码",required = true),
    })
    @RequestMapping(value = "/saveNamePhone",method = RequestMethod.POST)
    public Response saveNamePhone(HttpServletRequest request, @RequestParam String name, @RequestParam String phone
                                  ,@RequestParam String code,
                                  /*@RequestParam String inviteCode*/
                                  @RequestParam(required = false, value = "inviteCode", defaultValue = "") String inviteCode
                                  ) {
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

        KhXx khXx=(KhXx)request.getAttribute("khXx");
        khXx.setSj(phone);
        khXx.setXm(name);
        //通过短信验证后，就将当前用户的邀请码生成，然后保存、返回给前端
        if(StringUtils.isEmpty(khXx.getCode())){//如果当前用户没有生成过自己的邀请码，才生成
            String codeOnly = getOnlyCode();
            khXx.setCode(codeOnly);
            khXxService.update(khXx);
        }
        if(!StringUtils.isEmpty(inviteCode)){
            //如果邀请码不为空 则关联上级分销信息
            Response response = khXxService.checkInviteCode(inviteCode,khXx,"");
            return response;
        }
        return new Response(Code.SUCCESS);
    }

    /**
     * 获取唯一6位邀请码
     * @return
     */
    private String getOnlyCode() {
        String temp = RandomUtils.getRandomString(6);
        String khidParentOne = khXxDao.getUserIdByCode(temp);
        if(StringUtils.isEmpty(khidParentOne)){
            return temp;//如果不存在，直接返回
        }else{
            return getOnlyCode();
        }
    }

    //发送验证码 2买家 1卖家
    @ApiOperation(value = "send-register-sms",notes = "发送验证码",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone",value = "手机号",required = true)
    })
    @RequestMapping(value = "/send-register-sms",method = RequestMethod.POST)
    public Response<String> sendRegisterSms(@RequestParam String phone,
                                            @RequestParam(defaultValue = "1") String source) throws ClientException {
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
        map.put("source",source);
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


    @ApiOperation(value = "提现审核列表",httpMethod ="GET")
    @RequestMapping(value = "/txshList",method = RequestMethod.GET)
    public Page<Txsh> txshList(HttpServletRequest request,
                               @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                               @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Page<Txsh> txshPage = new Page<>();
        txshPage.setPageNo(page);
        txshPage.setPageSize(size);
        Txsh txsh = new Txsh();
        txsh.setKhid(khXx.getId());
        txsh.setPage(txshPage);
        List<Txsh> txshList = txshService.findList(txsh);
        return txshPage.setList(txshList);
    }

    @ApiOperation(value = "可提现金额",httpMethod ="GET")
    @RequestMapping(value = "/ktxje",method = RequestMethod.GET)
    public Response ktxje(HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        //可提现收益
        QyhsMx qyhsMx1 = new QyhsMx();
        qyhsMx1.setKhid(khXx.getId());
        qyhsMx1.setJszt(QyhsMx.STATUS_JS_WJS);
        String extColumn1 = "SUM(a.sy) AS \"sum\"";
        qyhsMx1.getSqlMap().add("extColumn", extColumn1);
        QyhsMx qyhsMx2 = qyhsMxDao.getByEntity(qyhsMx1);
        Double je = 0.0;
        if (qyhsMx2 != null){
            je = qyhsMx2.getSum();
        }
        return new Response(je);
    }

    /*@ApiOperation(value = "提现",httpMethod ="GET")
    @RequestMapping(value = "/tx",method = RequestMethod.GET)
    public Response tx(HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        //可提现收益
        QyhsMx qyhsMx1 = new QyhsMx();
        qyhsMx1.setKhid(khXx.getId());
        qyhsMx1.setJszt(QyhsMx.STATUS_JS_WJS);
        String extColumn1 = "SUM(a.sy) AS \"sum\"";
        qyhsMx1.getSqlMap().add("extColumn", extColumn1);
        QyhsMx qyhsMx2 = qyhsMxDao.getByEntity(qyhsMx1);
        Double je = 0.0;
        if (qyhsMx2 != null){
            je = qyhsMx2.getSum();
        }
        //判断提现金额是否大于0
        if (je == 0.0){
            return new Response(Code.API_TXJE_NULL);
        }
        //生成申请单
        Txsh txsh = new Txsh();
        txsh.setKhid(khXx.getId());
        txsh.setTxje(je);
        txsh.setZt(Txsh.TX_STATUS_SQZ);

        txshService.saveTxd(txsh);

        return new Response(Code.SUCCESS);
    }*/

    @ApiOperation(value = "绑定邀请码",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="inviteCode",value = "邀请码",required = true),
    })
    @RequestMapping(value = "/saveInviteCode",method = RequestMethod.POST)
    public Response saveInviteCode(HttpServletRequest request,
                                  @RequestParam(required = true, value = "inviteCode", defaultValue = "") String inviteCode){
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        if(khXx == null || StringUtils.isEmpty(khXx.getId())){
            return new Response(Code.API_NULL_AUTH);
        }
        //如果邀请码不为空 则关联上级分销信息
        return khXxService.checkInviteCode(inviteCode,khXx,"onlyCode");
    }

    //初始化所有已有用户邀请码 todo 生成之后立马注释掉
    @RequestMapping(value = "/initCode",method = RequestMethod.POST)
    public Response initCode(HttpServletRequest request){
        KhXx khXxTemp = new KhXx();
        khXxTemp.setType("2");
        List<KhXx> list = khXxService.findList(khXxTemp);
        for (KhXx khXx:list) {
            if(!StringUtils.isEmpty(khXx.getCode())){
                continue;
            }
            String codeOnly = getOnlyCode();
            khXx.setCode(codeOnly);
            khXxService.update(khXx);
        }
        return new Response(Code.SUCCESS);
    }


    //获取小程序二维码
    @RequestMapping(value = "/getUnlimited",method = RequestMethod.POST)
    public Response getUnlimited(HttpServletRequest request){
        KhXx khXx = (KhXx)request.getAttribute("khXx");
        if(StringUtils.isEmpty(khXx.getCode())){//如果当前用户没有生成过自己的邀请码，不能生成二维码
            return new Response("需要注册后生成自己的邀请码，才能获取邀请二维码！");
        }
        if(!StringUtils.isEmpty(khXx.getQr())){
            return new Response("已经获取过邀请二维码！");
        }

        Token token = TokenAPI.token(wxAppIdBuyer, wxAppSecretBuyer);
        if (!token.isSuccess()) {
            log.info("getUnlimited is fail.");
            return new Response(Code.API_USER_AUTH_ERROR);
        }
        String qrName = khXx.getOpenId();
        //String qrName = "123";
        String innerPath = "/userfiles/fileupload/qr/" + qrName + ".png";
        String filePath = baseDir  + innerPath;
        String tokenStr = token.getAccess_token();
        //你的json数据 ,格式不要错
        JSONObject jsonObject = new JSONObject();
        /*JSONObject jsonObjectInner = new JSONObject();
        jsonObjectInner.put("khid",khXx.getId());
        jsonObjectInner.put("code",khXx.getCode());*/
        //jsonObject.put("scene","code=" + khXx.getCode());
        jsonObject.put("scene",khXx.getCode());
        jsonObject.put("width",400);
        //String json = "{\"scene\":\"name=jerry\",\"width\":300}";
        String json = JSON.toJSONString(jsonObject);
        byte[] data = UnlimitAPI.myPost("/wxa/getwxacodeunlimit?access_token="+tokenStr,json);
        //new一个文件对象用来保存图片
        File imageFile = new File(filePath);
        //创建输出流
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输出流
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //更新到个人信息中
        khXx.setQr(innerPath);
        khXxService.update(khXx);
        return new Response(innerPath);
    }

    @RequestMapping(value = "/getMyExtend",method = RequestMethod.POST)
    public Response getMyExtend(HttpServletRequest request){
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        if(khXx == null || StringUtils.isEmpty(khXx.getId())){
            return new Response(Code.API_NULL_AUTH);
        }
        Map<String,Object> map = new HashMap<>();
        KhXx khXx1 = new KhXx();
        khXx1.setParentid(khXx.getId());
        List<KhXx> oneList = khXxService.findList(khXx1);
        //一级粉丝个数
        int childOneSize = 0;
        //二级粉丝个数
        int childTwoSize = 0;
        //今日粉丝个数
        int childTodaySize = 0;
        if(oneList != null && !oneList.isEmpty()){
            childOneSize = oneList.size();
            for(KhXx khXx2 : oneList){
                //判断该条1级粉丝的绑定时间是否是当天
                boolean boo = checkToday(khXx2.getBindDate());
                if(boo){
                    childTodaySize = childTodaySize + 1;
                }
                KhXx khXxTemp = new KhXx();
                khXxTemp.setParentid(khXx2.getId());
                List<KhXx> twoListTemp = khXxService.findList(khXxTemp);
                if(twoListTemp == null || twoListTemp.isEmpty()){
                    continue;
                }else{
                    for(KhXx khXx3 : twoListTemp){//遍历二级粉丝，检查是否是当天粉丝
                        boolean boo2 = checkToday(khXx3.getBindDate());
                        if(boo2){
                            childTodaySize = childTodaySize + 1;
                        }
                    }
                }
                childTwoSize = childTwoSize + twoListTemp.size();
            }
        }

        //我的收益
        String todayStr = getTodayStr();
        Double myMoney = 0.0;
        Double myMoneyDb = sellService.findMyMoney(khXx.getId(), Sell.SELL_STATUS_TG,todayStr);
        if(myMoneyDb != null){
            myMoney = myMoneyDb;
        }

        map.put("childOneSize", childOneSize);
        map.put("childTwoSize", childTwoSize);
        map.put("childTodaySize", childTodaySize);
        map.put("myMoney", myMoney);
        return new Response(map);
    }

    private String getTodayStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    private boolean checkToday(Date date) {
        boolean boo = false;
        if(date == null){
            return boo;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dbDate = format.format(date);
        String nowDate = format.format(new Date());
        if(nowDate.equals(dbDate)){
            return true;
        }
        return boo;
    }

    @RequestMapping(value = "/getMyChildOneList",method = RequestMethod.POST)
    public Page<KhXx> getMyChildOneList(HttpServletRequest request,
                                      @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(required = false, value = "size", defaultValue = "10") Integer size){
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Page<KhXx> khXxPage = new Page<>();
        khXxPage.setPageNo(page);
        khXxPage.setPageSize(size);
        if(khXx == null || StringUtils.isEmpty(khXx.getId())){
            return khXxPage;
        }
        KhXx khXx1 = new KhXx();
        khXx1.setParentid(khXx.getId());
        khXx1.setPage(khXxPage);
        List<KhXx> oneList = khXxService.findList(khXx1);
        khXxPage.setList(oneList);
        return khXxPage;
    }

    @RequestMapping(value = "/getMyChildTwoList",method = RequestMethod.POST)
    public Page<KhXx> getMyChildTwoList(HttpServletRequest request,
                                      @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(required = false, value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(required = false, value = "childOneId", defaultValue = "") String childOneId){
        Page<KhXx> pageResult = new Page<KhXx>();
        pageResult.setPageNo(page);
        pageResult.setPageSize(size);
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        if(khXx == null || StringUtils.isEmpty(khXx.getId())){
            return pageResult;
        }
        List<KhXx> twoList = new ArrayList<>();
        KhXx khXx1 = new KhXx();
        khXx1.setParentid(khXx.getId());
        List<KhXx> oneList = khXxService.findList(khXx1);
        if(oneList != null && !oneList.isEmpty()){
            for(KhXx khXx2 : oneList){
                //如果传递1级粉丝，则获取对应1级粉丝下面的二级粉丝
                if(!StringUtils.isEmpty(childOneId) && !childOneId.equals(khXx2.getId())){
                    continue;
                }
                KhXx khXxTemp = new KhXx();
                khXxTemp.setParentid(khXx2.getId());
                List<KhXx> twoListTemp = khXxService.findList(khXxTemp);
                if(twoListTemp == null || twoListTemp.isEmpty()){
                    continue;
                }else{
                    for(KhXx khXx3 : twoListTemp){//遍历二级粉丝，设置对应的一级粉丝
                        khXx3.setParentInfo(khXx2);
                    }
                    twoList.addAll(twoListTemp);
                }
            }
        }else{
            log.info("当前用户没有一级粉丝，没有二级粉丝！");
            return pageResult;
        }
        Paper<KhXx> paper = new Paper<KhXx>(page,size,twoList);//paper.getDataList()就是子数组数据
        pageResult.setCount(twoList.size());
        pageResult.setList(paper.getDataList());
        return pageResult;
    }

    @RequestMapping(value = "/getMyMoney",method = RequestMethod.POST)
    public Page<Sell> getMyMoney(HttpServletRequest request,
                                        @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(required = false, value = "size", defaultValue = "10") Integer size){
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        String todayStr = getTodayStr();
        Double myMoney = sellService.findMyMoney(khXx.getId(), Sell.SELL_STATUS_TG,todayStr);
        Page<Sell> pageResult = new Page<>();
        pageResult.setPageNo(page);
        pageResult.setPageSize(size);
        Sell sell = new Sell();
        sell.setPage(pageResult);
        sell.setKhid(khXx.getId());
        List<Sell> list = sellService.findList(sell);
        pageResult.setList(list);
        return pageResult;
    }
}
