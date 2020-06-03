package com.jeesite.API.controller.bright;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "订单相关接口")
@RestController
@RequestMapping("${apiPath}/order")
public class ApiOrderController {

    /*@Autowired
    private OrderService orderService;*/

    /*@ApiOperation(value = "getOrderAll",notes = "获取所有订单",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页",required = false),
            @ApiImplicitParam(name="size",value = "每页多少条",required = false),
            @ApiImplicitParam(name="状态",value = "全部为''，待付 1 待发货 3 已发货4 已完成 5 上门自提 6",required = false)
    })
    @RequestMapping(value = "/getOrderAll",method = RequestMethod.GET)
    public Response getOrderAll(HttpServletRequest request, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                @RequestParam(required = false, value = "size", defaultValue = "10") Integer size,
                                String zt) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        return new Response(orderService.findAllList(page, size, khXx.getId(), zt));
    }

    @ApiOperation(value = "getOrder",notes = "获取订单详情",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "订单id",required = false)
    })
    @RequestMapping(value = "/getOrder",method = RequestMethod.GET)
    public Response getOrder(String id) {
        return new Response(orderService.getOrder(id));
    }

    @ApiOperation(value = "confirmSh",notes = "确认收货",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "订单id",required = true),
            @ApiImplicitParam(name="zt",value = " 2 已自提 5 已收货 ",required = true)
    })
    @RequestMapping(value = "/confirmSh",method = RequestMethod.GET)
    public Response confirmSh(@RequestParam String id, @RequestParam int zt) {
        orderService.confirmSh(id, zt);
        return new Response(Code.SUCCESS);
    }*/
}
