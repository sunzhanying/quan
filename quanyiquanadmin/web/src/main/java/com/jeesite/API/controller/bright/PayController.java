package com.jeesite.API.controller.bright;

import com.aliyuncs.exceptions.ClientException;
import com.google.common.base.Strings;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.PayService;
import com.jeesite.API.service.Response;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyhsmx.service.QyhsMxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(description = "微信支付接口接口")
@RestController
@RequestMapping("${apiPath}/wx")
public class PayController {

    @Autowired
    private PayService payService;
    @Autowired
    private QyhsMxService qyhsMxService;

    private final Log logger = LogFactory.getLog(getClass());

    //买家付款
    @ApiOperation(value = "pay",notes = "微信支付",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="spId",value = "权益id",required = true),
            @ApiImplicitParam(name="sl",value = "数量",required = true),
            @ApiImplicitParam(name="orderId",value = "订单id",required = false),
    })
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public synchronized Response pay(HttpServletRequest request, @RequestParam String spId,
                                     @RequestParam Integer sl, String orderId) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        logger.info("买家付款接口参数：sl:" + sl + "; spId:" + spId + "; orderId:" + orderId);
        //检查权益库存
        //有订单id时不检查库存
        if (Strings.isNullOrEmpty(orderId)){
            QyhsMx qyhsMx = new QyhsMx();
            qyhsMx.setQyqId(spId);
            qyhsMx.setZt(QyhsMx.STATUS_CSZ);
            if (qyhsMxService.findCount(qyhsMx) < sl){
                return new Response(Code.API_QYQ_KC_NULL);
            }
        }
        Order order = new Order();
        order.setId(orderId);
        order.setSpId(spId);
        order.setSl((long)sl);
        order.setUserId(khXx.getId());
        return (Response) payService.pay(request, khXx, order);
    }


/**
 * 微信支付发起（VIP）
 *  @param type 1手动付费 2自动续费
 * @param request
 * @return
 *//*
    @RequestMapping(value = "/vipPay",method = RequestMethod.POST)//
    public Response vip_pay(HttpServletRequest request,
                            @RequestParam(value = "type",required = true)String type) {
        if(!type.equals(VipOrder.VIP_PAYTYPE_OWN)&&!type.equals(VipOrder.VIP_PAYTYPE_AUTO)){
            return new Response(Code.API_PARA_ERROR);
        }
        return new Response(payService.vipPay(request,type));
    }

    */

    /**
     * 支付取消
     */

   /* @RequestMapping(value = "/canselPay", method = RequestMethod.POST)
    public Response canselPay(HttpServletRequest request, HttpServletResponse response, String orderId) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");

        return new Response(payService.canselPay(khXx, orderId));
    }*/

    /**
     * 商品支付回调
     */

    @RequestMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException,ClientException {
        logger.info("买家支付成功进入回调函数");
        payService.payNotify(request, response);
    }

    /**
     * 订单详情
     */
    /*@RequestMapping(value = "/orderDetail",method = RequestMethod.GET)
    public Response orderDetail(HttpServletRequest request, String orderId){
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        Order order=orderService.get(orderId);
        KhCcmx khCcmx  =  khCcmxDao.khccmxbyDid(orderId);
        if (khCcmx != null){
            order.setCode(khCcmx.getPzm());
        }
        if(order.getUserId().equals(khXx.getId())){
            return new Response(order);
        }
        return new Response(Code.API_USER_ROLE_ERROR);
    }*/

}
