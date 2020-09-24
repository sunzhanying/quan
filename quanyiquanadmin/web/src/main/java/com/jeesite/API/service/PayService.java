
package com.jeesite.API.service;

import com.aliyuncs.exceptions.ClientException;
import com.google.common.base.Strings;
import com.jeesite.API.weixin.api.PayMchAPI;
import com.jeesite.API.weixin.bean.paymch.MchBaseResult;
import com.jeesite.API.weixin.bean.paymch.MchPayNotify;
import com.jeesite.API.weixin.bean.paymch.Unifiedorder;
import com.jeesite.API.weixin.bean.paymch.UnifiedorderResult;
import com.jeesite.API.weixin.support.ExpireKey;
import com.jeesite.API.weixin.support.expirekey.DefaultExpireKey;
import com.jeesite.API.weixin.util.*;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.service.SpXxService;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.util.KhXwUtil;
import com.jeesite.modules.bright.util.OrderManager;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.order.service.OrderService;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyhsmx.service.QyhsMxService;
import com.jeesite.modules.qyjg.entity.Qyjg;
import com.jeesite.modules.qyjg.service.QyjgService;
import com.jeesite.modules.sale.entity.Sale;
import com.jeesite.modules.sale.service.SaleService;
import com.jeesite.modules.txsh.entity.Txsh;
import com.jeesite.modules.txsh.service.TxshService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支付Service
 *
 * @author 李金辉
 * @version 2018-05-24
 */

@Service
@Transactional(readOnly = true)
public class PayService {
    @Value("${weixin.appidA}")
    private String wxAppId;

    @Value("${weixin.Mch_id}")
    private String Mch_id;

    @Value("${weixin.Notify_url}")
    private String Notify_url;

    @Value("${weixin.Mch_key}")
    private String key;

    @Value("${admin_phone}")
    private String adminPhone;

    @Autowired
    private OrderService orderService;
    @Autowired
    private QyjgService qyjgService;
    @Autowired
    private SpXxService spXxService;
    @Autowired
    private QyhsMxService qyhsMxService;
    @Autowired
    private TxshService txshService;
    @Autowired
    private SaleService saleService;

    String product_id = "";
    String body = "";

    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    private final Log log = LogFactory.getLog(getClass());

    //微信支付
    @Transactional(readOnly = false)
    public Object pay(HttpServletRequest request, KhXx khXx, Order order) {

        Order order1 = null;   //订单
        //有订单时
        if (!Strings.isNullOrEmpty(order.getId())){
            System.out.println("========有订单时=============");
            order1 = (Order) OrderManager.getOrder(order.getId());
            if (order1 == null){
                return new Response(Code.API_ORDER_ZDQXZF);
            }
        }else{ //无订单
            //显示当前价
            Qyjg qyjg = new Qyjg();
            qyjg.setQyqId(order.getSpId());
            qyjg.setPageSize(1);
            Qyjg qyjg1 = qyjgService.findList(qyjg).get(0);
            order.setActualPayment(qyjg1.getCsj()*order.getSl());
            order.setPayment(qyjg1.getCsj()*order.getSl());
            order.setJgid(qyjg1.getId());
            order.setHsj(qyjg1.getHsj());//该回收价为最新回收价，不准确，改为根据上传时的回收价，在卖券中
            order.setScj(qyjg1.getCsj());
            order.setZt(Order.PAY_STATUS_DZF);
            order.preInsert();
            Response response = orderService.qxOrderLjSpKc(false, order);
            if (response.getCode() != 200){
                return response;
            }
            //map中添加订单
            OrderManager.addOrder(order);
            //添加客户行为
            KhXwUtil.addXw("17",khXx.getId(), body,(String)response.getData());
            order1 = order;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(order1.getActualPayment()));
        BigDecimal b2 = new BigDecimal(Double.toString(100));

        log.info("》》》》》》》》》》"+order1.getActualPayment());
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setProduct_id(product_id);
        unifiedorder.setBody(spXxService.get(order1.getSpId()).getSpmc());
        unifiedorder.setAttach("");
        unifiedorder.setTotal_fee(String.valueOf((int)b1.multiply(b2).doubleValue()));//单位分
        unifiedorder.setAppid(wxAppId);
        unifiedorder.setMch_id(Mch_id);
        unifiedorder.setGoods_tag("APIUYHGBJG");
        unifiedorder.setOpenid(khXx.getOpenId());
        unifiedorder.setNonce_str(IdGen.wxRandom(32));
        unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
        unifiedorder.setOut_trade_no(order1.getId());
        unifiedorder.setNotify_url(Notify_url);
        unifiedorder.setTrade_type("JSAPI");
        log.info(unifiedorder);
        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
        log.info(unifiedorderResult);
        log.info("获取与支付订单号回执：" + unifiedorderResult.getReturn_msg());
        //成功返回
        if ("SUCCESS".equals(unifiedorderResult.getResult_code()) && "SUCCESS".equals(unifiedorderResult.getReturn_code())) {
            System.out.println("调起支付成功订单id: " + order1.getId());
            List list1=new ArrayList();
            list1.add(PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(),wxAppId,key));
            list1.add(order1.getId());
            return new Response(list1);
        } else {
            return new Response(Code.API_ORDER_PAY_ERROR);
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

                Order order = orderService.get(payNotify.getOut_trade_no());//付款完回调时传递订单id
                System.out.println("回调数据："+payNotify.toString());

                //未支付成功时走业务
                if (Order.PAY_STATUS_DZF.equals(order.getZt())){
                    //删除map中元素
                    OrderManager.removeOrder(order.getId());
                    //更新订单状态
                    order.setZt(Order.PAY_STATUS_YZF);
                    order.setTransactionId(payNotify.getTransaction_id());
                    order.setPayTime(new Date());
                    orderService.update(order);
                    //权益明细
                    QyhsMx qyhsMx = new QyhsMx();
                    qyhsMx.setOrderId(order.getId());
                    List<QyhsMx> qyhsMxList = qyhsMxService.findList(qyhsMx);
                    qyhsMxList.forEach(item ->{
                        item.setZt(QyhsMx.STATUS_YFK);
                        item.setJszt(QyhsMx.STATUS_JS_WJS);
                        //item.setSy(order.getHsj());//回收价以用户上传时的价格为准
                        item.setSellPrice(order.getScj());//售出价以下单时的价格为准，赋值给具体的卖券,方便后期计算收益
                        qyhsMxService.update(item);
                    });
                    //分单，向提现表中添加数据
                    //查找该订单权益出售人信息
                    QyhsMx qyhsMx1 = new QyhsMx();
                    qyhsMx1.setOrderId(order.getId());
                    String extColumn = "SUM(a.sy) AS \"sum\"";
                    qyhsMx1.getSqlMap().add("extColumn", extColumn);
                    qyhsMx1.getSqlMap().add("extWhere", "GROUP BY a.khid");
                    List<QyhsMx> qyhsMxList1 = qyhsMxService.findList(qyhsMx1);
                    //分别生成提现单
                    qyhsMxList1.forEach(item ->{
                        //生成申请单
                        Txsh txsh = new Txsh();
                        txsh.setKhid(item.getKhid());
                        txsh.setTxje(item.getSum());
                        txsh.setZt(Txsh.TX_STATUS_SQZ);
                        txsh.setOrderId(order.getId());
                        txsh.setType(Txsh.TX_TYPE_SELL);
                        txshService.saveTxd(txsh);
                    });
                    //二级分销分利润,根据券类型表a_qyhs_mx 中获取固定收益，或者百分比，百分比的话需要查询出关联的卖券
                    doSaleInfo(order);
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

    //处理二级分销分利润
    private void doSaleInfo(Order order) {
        try{
            String buyerId = order.getUserId();
            Long sl = order.getSl();//单个固定收益要乘以数量才行
            SpXx spXx = spXxService.get(order.getSpId());
            //根据买家id，获取父1级khid
            Double txjeOne = spXx.getMoneyOne();
            if(txjeOne == null || txjeOne <= 0){
                return;//如果没有设置分润则直接返回
            }
            String parentTemp = doWithIncome(order,buyerId, txjeOne * sl);//处理父1级
            if(StringUtils.isEmpty(parentTemp)){//如果父2级不存在
                return;
            }
            Double txjeTwo = spXx.getMoneyTwo();
            if(txjeTwo == null || txjeTwo <= 0){
                return;//如果没有设置分润则直接返回
            }
            doWithIncome(order,parentTemp, txjeTwo * sl);//处理父2级
        }catch (Exception e){
            log.error("下单后封装分销收益报错,order id =" + order.getId(),e);
        }
    }

    //处理上一级收益
    private String doWithIncome(Order order, String buyerId, Double txje) {
        String parentTemp = "";//上一级khid
        List<Sale> list = saleService.getSaleListByKhid(buyerId);
        if(list != null && !list.isEmpty()){
            for(Sale saleEntity :list){
                if(!StringUtils.isEmpty(saleEntity.getParentOne())){
                    parentTemp = saleEntity.getParentOne();
                    break;//上家只能有一个
                }
            }
        }else{
            return parentTemp;//没有父1级直接返回
        }
        //将父1级收益保存到提现表中，提现记录
        if(StringUtils.isEmpty(parentTemp)){
            return parentTemp;//没有父1级直接返回
        }
        //生成父1级提现申请单
        Txsh txsh = new Txsh();
        txsh.setKhid(parentTemp);//todo 目前前端展示的是关联卖家的昵称信息，order中是买家
        txsh.setTxje(txje);
        txsh.setZt(Txsh.TX_STATUS_SQZ);
        txsh.setOrderId(order.getId());
        txsh.setType(Txsh.TX_TYPE_BUY);
        txshService.save(txsh);
        return parentTemp;
    }

    //支付成功调用
   /* public void paySuccess(Order order){
        System.out.println("金额：" + order.getPayment());
        //增加积分
        Long jf = (long) Math.floor(Double.valueOf(order.getPayment()));
        if (jf > 0){
            KhXx khXx = khXxService.get(order.getUserId());
            PointsConfig pointsConfig = pointsConfigService.get(PointsConfig.POINTSCONFIG_PAY);
            pointsLogService.savePoints(khXx, pointsConfig, jf);
        }
        //删除map中元素
        OrderManager.removeOrder(order.getId());
        //更新订单支付状态
        order.setZt(Order.ORDER_PAY_WC);
        order.setPayTime(new Date());
        order.setEndTime(new Date());
        if (order.getPsFs() == 1){
            order.setDdZt((long)Order.ORDER_STATUS_WT);
        }else{
            order.setDdZt((long)Order.ORDER_STATUS_WFH);
        }
        int count = orderDao.updateOrder(order);
        if (count > 0){
            //有会员卡生成消费记录
            if(!Strings.isNullOrEmpty(order.getVipCardId())){
                KhVipcard khVipcard = khVipcardService.get(order.getVipCardId());

                System.out.println("原金额" + khVipcard.getYe());
                System.out.println("抵扣金额" + order.getDkje());

                BigDecimal b1 = new BigDecimal(Double.toString(khVipcard.getYe()));
                BigDecimal b2 = new BigDecimal(Double.toString(order.getDkje()));
                VipcardJl vipcardJl = new VipcardJl();
                vipcardJl.setVipCardId(order.getVipCardId());
                vipcardJl.setKhid(order.getUserId());
                vipcardJl.setDkje(order.getDkje());
                vipcardJl.setYe(b1.subtract(b2).doubleValue());
                vipcardJl.setKcfs(VipcardJl.VIP_CARD_KCFS_XS);
                //添加会员卡使用记录
                vipcardJlService.save(vipcardJl);
                //更新会员卡余额
                khVipcard.setYe(b1.subtract(b2).doubleValue());
                khVipcardService.update(khVipcard);
                //修改第三方会员的余额
                try {
                    KhXx khXx = khXxService.get(order.getUserId());
                    zyAPI.updateBalancePointByIncrement(String.valueOf(khXx.getCrmid()), -order.getDkje(), 0.00);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //向管理员发送短信
            if (Order.ORDER_TYPE_SP.equals(order.getType()) || Order.ORDER_TYPE_GWC.equals(order.getType())){
                System.out.println("向管理员发送短信通知");
                System.out.println("金额：" + order.getPayment());
                System.out.println("名称：" + order.getOrderName());

                String price = String.valueOf(order.getPayment());
                String name = order.getOrderName();
                if (Strings.isNullOrEmpty(price)){
                    price = "0.0";
                }
                if (Strings.isNullOrEmpty(name)){
                    name = "默认商品";
                }

                Map map=new HashMap();
                map.put("price",price);
                map.put("name",name);
                //阿里
                try {
                    String[] str = adminPhone.split(",");
                    for (String s:str){
                        SendSmsResponse result = SmsUtil.sendSms(SmsUtil.SMS_CODE_GOODS, s, map);
                        System.out.println(result.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }*/


    /*
    //购买会员时更新用户信息
    public void updateVip(Order order) throws ParseException{
        Date date=null;
        VipXx vipXx=vipXxService.get(order.getContentId());
        //年
        if("1".equals(vipXx.getSxxz())){
            date= MyUtils.plusDay(vipXx.getHysx()*12*30);
            //月
        }else if("2".equals(vipXx.getSxxz())){
            date= MyUtils.plusDay(vipXx.getHysx()*30);
            //日
        }else{
            date= MyUtils.plusDay(vipXx.getHysx());
        }
        KhXx khXx= khXxDao.get(order.getUserId());
        khXx.setVipid(order.getContentId());
        khXx.setHyDate(new Date());
        khXx.setSxDate(date);
        khXx.preUpdate();
        khXxDao.update(khXx);
    }

    public void setKhcc(Order order) throws ParseException {
        //VIP订单时
        if(Order.TYPE_VIP.equals(order.getType())){
            Date date=null;
            VipXx vipXx=vipXxService.get(order.getContentId());
            //年
            if("1".equals(vipXx.getSxxz())){
                date= MyUtils.plusDay(vipXx.getHysx()*12*30);
                //月
            }else if("2".equals(vipXx.getSxxz())){
                date= MyUtils.plusDay(vipXx.getHysx()*30);
                //日
            }else{
                date= MyUtils.plusDay(vipXx.getHysx());
            }
            KhXx khXx= khXxDao.get(order.getUserId());
            khXx.setVipid(order.getContentId());
            khXx.setHyDate(new Date());
            khXx.setSxDate(date);
            khXx.preUpdate();
            khXxDao.update(khXx);
            //循环商品包
            *//*for (SpSpbmx spSpbmx:vipXx.getSpSpbdy().getSpbmxes()) {
                if ("4".equals(spSpbmx.getSpXx().getJldw())){
                    if (order.getXsid()!=null && !"".equals(order.getXsid())){
                        if (KhCc.TYPE_4.equals(spSpbmx.getSpid())){//生涯任务
                            //更新学生权限
                            //updateStudentRole(order.getXsid(),1);
                        }
                    }
                    //更新或添加持仓
                    updateKhcc(order.getUserId(),spSpbmx.getSpid(),order.getId(),date,spSpbmx.getSpXx().getJldw(),spSpbmx.getSl(),order.getXsid(),order.getType());
                }else{
                    //循环商品个数
                    for(int a=0;a<spSpbmx.getSl();a++){
                        updateKhcc(order.getUserId(),spSpbmx.getSpid(),order.getId(),date,null,null,null,order.getType());
                    }
                }
            }*//*

           //* //添加优惠券
            VipYhq vipYhq=new VipYhq();
            vipYhq.setHyid(vipXx.getId());
            List<VipYhq> list=vipYhqDao.findList(vipYhq);
            KhYhq khYhq=new KhYhq();
            khYhq.setKhid(khXx.getId());
            SpYhq spYhq;
            for (VipYhq v: list) {
                spYhq= spYhqDao.get(v.getSpYhq());
                khYhq.setSpYhq(spYhq);
                khYhq.setStatus(KhYhq.STATUS_NORMAL);
                //
                if("1".equals(spYhq.getYhSxlx())){
                    khYhq.setStartDate(spYhq.getYhStart());
                    khYhq.setEndDate(spYhq.getYhEnd());
                }else{
                    khYhq.setStartDate(new Date());
                    khYhq.setEndDate(MyUtils.plusDay(Integer.valueOf(spYhq.getYhRsx())));
                }
                khYhqDao.insert(khYhq);
            }
        }else{
            Date date = null;
            String xsid = null;
            //SpXx spXx = spxxService.get(order.getGoodsId());
            //开通类商品
            *//*if ("4".equals(spXx.getJldw())&&(KhCc.TYPE_4.equals(order.getGoodsId())
                    ||KhCc.TYPE_8.equals(order.getGoodsId()))){
                date = khXxDao.get(order.getUserId()).getSxDate();
                xsid = order.getXsid();
                if (KhCc.TYPE_4.equals(order.getGoodsId())){//生涯任务
                    //更新学生权限
                    updateStudentRole(xsid,1);
                }
            }*//*
            //updateKhcc(order.getUserId(),order.getGoodsId(),order.getId(),date,spXx.getJldw(),1,xsid,order.getType());
        }
    }

    private void updateKhcc(String userId,String goodsId,String OrderId,Date date,String jldw,Integer shu,String xsid,String type) {
        //更新用户持仓
        KhCc khcc = new KhCc();
        khcc.setSpid(goodsId);
        khcc.setKhid(userId);
        List<KhCc> list = khCcService.findList(khcc);

        if (!"4".equals(jldw)){
            System.out.println("================"+list.size());
            //如果存在此持仓
            if (list.size() != 0) {
                khcc = list.get(0);
                khcc.setZsl( khcc.getZsl()+1);
                khcc.setKysl(khcc.getKysl()+1);
            }else{
                khcc.setZsl(1);
                khcc.setKysl(1);
            }
        }else{
            //如果存在此持仓
            if (list.size() != 0) {
                khcc = list.get(0);
                khcc.setZsl(shu);
                khcc.setKysl(shu);
                //刪除持仓明细
                //khCcmxDao.deleteKhccmx(khcc.getId(),goodsId);
            }else{
                khcc.setZsl(shu);
                khcc.setKysl(shu);
           }
        }
        khcc.setDdid(OrderId);
        khCcService.save(khcc);
        KhCcmx khCcmx = new KhCcmx();
        khCcmx.setKhccid(khcc);
        khCcmx.setDdid(OrderId);
        khCcmx.setSpid(goodsId);
        khCcmx.setXsid(xsid);
        //活动添加凭证吗
        if(Order.TYPE_ACTIVE.equals(type)){
            khCcmx.setCode(IdGen.randomBase62(5));
        }
        khCcmx.setHqrq(new Date());
        khCcmx.setSxrq(date);
        khCcmx.setZt(KhCcmx.STATUS_NORMAL);
        khCcmx.preInsert();
        khCcmxDao.insert(khCcmx);
    }

    @Transactional(readOnly = false)
    public Object canselPay(KhXx khXx, String orderId) {
        Order order=orderDao.get(orderId);
        if(!khXx.getId().equals(order.getUserId())){
            return Code.API_PARA_ERROR;
        }
        order.setStatus(Order.STATUS_CLOSE);
        order.preUpdate();
        orderDao.update(order);
        return Code.SUCCESS;
    }*/


   /* @Transactional(readOnly = false)
    public Object vipPay(HttpServletRequest request,String type) {
        Users users=(Users)request.getAttribute("users");
        //0订单id 1订单金额
        List<String> list= vipOrderService.orderSave(users,type);
        if(list.get(0)==null){
            return new Response(Code.API_PAY_ORDER_FAIL);
        }
        Unifiedorder unifiedorder =new Unifiedorder();
        unifiedorder.setProduct_id("1");
        unifiedorder.setBody("甜藕VIP");
        unifiedorder.setAttach("甜藕黑卡会员");

        unifiedorder.setTotal_fee(String.valueOf((int)(Double.valueOf(list.get(1))*100)));//单位分
        unifiedorder.setAppid(wxAppIdToC);
        unifiedorder.setMch_id(Mch_id);
        unifiedorder.setOpenid(users.getOpenId());
        unifiedorder.setNonce_str(IdGen.wxRandom(32));
        unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
        unifiedorder.setOut_trade_no(list.get(0));
        unifiedorder.setNotify_url(vip_Notify_url);
        unifiedorder.setTrade_type("JSAPI");
        log.info(unifiedorder);
        UnifiedorderResult unifiedorderResult= PayMchAPI.payUnifiedorder(unifiedorder,key);
        log.info(unifiedorderResult);
        log.info("获取与支付订单号回执："+unifiedorderResult.getReturn_msg());
        if("SUCCESS".equals(unifiedorderResult.getResult_code())&&"SUCCESS".equals(unifiedorderResult.getReturn_code())){
            Map map=new HashMap<>();
            map.put("message",PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(),wxAppIdToC,key ));
            map.put("orderId",list.get(0));
            return new Response(map);
        }else{
            return new Response("获取预支付订单号失败");
        }

    }

    //vip支付回调
    @Transactional(readOnly = false)
    public void vipPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        //获取请求数据
        String xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
        //将XML转为MAP,确保所有字段都参与签名验证
        Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
        //转换数据对象
        MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xmlData);
        //已处理 去重

        if (payNotify==null || expireKey.exists(payNotify.getTransaction_id())) {
            return;
        }else{
            //签名验证成功
            if (SignatureUtil.validateSign(mapData, key)) {
                //订单处理
                expireKey.add(payNotify.getTransaction_id());
                VipOrder vipOrder=vipOrderDao.get(payNotify.getOut_trade_no());
                vipOrder.setStatus(VipOrder.VIP_STATUS_PAYED);
                vipOrder.setPayTime(new Date());
                vipOrder.setTransactionId(payNotify.getTransaction_id());
                vipOrder.preUpdate();
                vipOrderDao.update(vipOrder);
                MchBaseResult baseResult = new MchBaseResult();
                baseResult.setReturn_code("SUCCESS");
                baseResult.setReturn_msg("OK");
                //用户处理
                Users users=usersDao.findUserByOpenId(payNotify.getOpenid());
                users.setIsVip(Users.USER_VIP_YES);
                users.setActivetime(new Date());
                users.setVipDays(String.valueOf(users.getVipDays()==null?31:Integer.valueOf(users.getVipDays())+31));
                users.preUpdate();
                usersDao.update(users);
                //用户消息
                UserNews userNews=new UserNews();
                userNews.setUserId(users.getId());
                userNews.setTitle("您已成功开通甜藕会员卡!");
                userNews.setContent("立即享受权益吧！");
                userNews.setIsRead("2");
                userNews.setType(UserNews.TYPE_VIP);
                userNewsService.save(userNews);
                //推送
                List words=new ArrayList();
                words.add(users.getNickname());
                words.add(TimeUtils.formatTime(new Date()));
                words.add("甜藕VIP");
                //words.add(MyUtils.plusDay(Integer.valueOf(users.getVipDays()),new Date()));
                pushUtil.pushC(payNotify.getOpenid(), PushUtil.TID_TOC_PAY_VIP_CARD,words,vipOrder.getId());
                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            } else {
                MchBaseResult baseResult = new MchBaseResult();
                baseResult.setReturn_code("FAIL");
                baseResult.setReturn_msg("ERROR");
                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            }
        }
    }*/

}
