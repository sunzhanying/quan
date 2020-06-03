package com.jeesite.API.controller.bright;

import com.google.common.base.Strings;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.yhdz.entity.KhDz;
import com.jeesite.modules.bright.yhdz.service.KhDzService;
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

@Api(description = "用户地址的接口")
@RestController
@RequestMapping("${apiPath}/yhdz")
public class ApiYhDzController {

    @Autowired
    private KhDzService khDzService;

    @ApiOperation(value = "getDzAll",notes = "获取用户收货地址列表",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="isDefault",value = "加此参数返回默认地址且 必须传 1 ",required = false)
    })
    @RequestMapping(value = "/getDzAll",method = RequestMethod.GET)
    public Response getSpAll(HttpServletRequest request, String isDefault) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        KhDz khDz = new KhDz();
        khDz.setUserId(khXx.getId());
        if (!Strings.isNullOrEmpty(isDefault)){
            khDz.setIsDefault(isDefault);
        }
        return new Response(khDzService.findList(khDz));
    }

    @ApiOperation(value = "getDzDetails",notes = "获取用户收货地址详情",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "地址id",required = true)
    })
    @RequestMapping(value = "/getDzDetails",method = RequestMethod.GET)
    public Response getDzDetails(@RequestParam String id) {
        return new Response(khDzService.get(id));
    }

    @ApiOperation(value = "deleteDz",notes = "删除用户收货地址",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "地址id",required = true)
    })
    @RequestMapping(value = "/deleteDz",method = RequestMethod.GET)
    public Response deleteDz(@RequestParam String id) {
        KhDz khDz = new KhDz();
        khDz.setId(id);
        khDzService.delete(khDz);
        return new Response(Code.SUCCESS);
    }

    @ApiOperation(value = "saveDz",notes = "保存或修改用户收货地址",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "地址id(有id修改无添加)",required = false),
            @ApiImplicitParam(name="name",value = "姓名",required = true),
            @ApiImplicitParam(name="phone",value = "手机号",required = true),
            @ApiImplicitParam(name="province",value = "省",required = true),
            @ApiImplicitParam(name="city",value = "城市",required = true),
            @ApiImplicitParam(name="area",value = "地区",required = true),
            @ApiImplicitParam(name="address",value = "详细地址",required = true),
            @ApiImplicitParam(name="sex",value = "性别1男2女",required = true),
            @ApiImplicitParam(name="bq",value = "标签",required = false),
            @ApiImplicitParam(name="isDefault",value = "默认地址 填1",required = false)
    })
    @RequestMapping(value = "/saveDz",method = RequestMethod.POST)
    public Response saveDz(HttpServletRequest request, String id, @RequestParam String name, @RequestParam String phone,
                           @RequestParam String province, @RequestParam String city, @RequestParam String area, @RequestParam String address, String isDefault,
                           @RequestParam String sex, String bq) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        KhDz khDz = new KhDz();
        khDz.setId(id);
        khDz.setUserId(khXx.getId());
        khDz.setName(name);
        khDz.setPhone(phone);
        khDz.setProvince(province);
        khDz.setCity(city);
        khDz.setArea(area);
        khDz.setAddress(address);
        khDz.setIsDefault(isDefault);
        khDz.setSex(Integer.valueOf(sex));
        khDz.setBq(bq);
        khDzService.save(khDz);
        return new Response(Code.SUCCESS);
    }
}
