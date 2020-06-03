package com.jeesite.API.controller.bright;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.modules.bright.khyhq.dao.KhYhqDao;
import com.jeesite.modules.bright.khyhq.service.KhYhqService;
import com.jeesite.modules.bright.sp.entity.yhq.SpYhq;
import com.jeesite.modules.bright.sp.service.yhq.SpYhqService;
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

@Api(description = "优惠券的接口.")
@RestController
@RequestMapping("${apiPath}/yhq")
public class ApiYhqController {

    @Autowired
    private KhYhqService khYhqService;
    @Autowired
    private SpYhqService spYhqService;


    @ApiOperation(value = "lqYhq",notes = "领取优惠券",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="yhqid",value = "优惠券id",required = true)
    })
    @RequestMapping(value = "/lqYhq",method = RequestMethod.GET)
    public Response lqYhq(HttpServletRequest request, @RequestParam String yhqid) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        return khYhqService.saveKhYhq(yhqid, khXx.getId());
    }


    @ApiOperation(value = "yhqList",notes = "获取所有优惠券",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页",required = false),
            @ApiImplicitParam(name="size",value = "每页多少条",required = false),
            @ApiImplicitParam(name="zt",value = "1 未使用 2 已使用 3 已过期",required = true),
            @ApiImplicitParam(name="je",value = "金额",required = false)
    })
    @RequestMapping(value = "/yhqList",method = RequestMethod.GET)
    public Response yhqList(HttpServletRequest request, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                @RequestParam(required = false, value = "size", defaultValue = "10") Integer size,
                                @RequestParam String zt, String je) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        PageHelper.startPage(page,size);
        return new Response(new PageInfo<>(khYhqService.findAllList(khXx.getId(), Integer.parseInt(zt), je)));
    }

    @ApiOperation(value = "userYhqList",notes = "弹出优惠券",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userType",value = "用户类型 1 新用户 2 会员用户",required = true)
    })
    @RequestMapping(value = "/userYhqList",method = RequestMethod.GET)
    public Response userYhqList(HttpServletRequest request, @RequestParam String userType) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        SpYhq spYhq = new SpYhq();
        spYhq.setUserType(userType);
        spYhq.setSxj(SpYhq.YHQ_SJ);
        return spYhqService.findListByUserType(spYhq, khXx.getId());
    }

}
