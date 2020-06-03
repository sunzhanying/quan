/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.ctyd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.ctyd.entity.Ctyd;
import com.jeesite.modules.bright.ctyd.service.CtydService;

/**
 * 餐厅预订表Controller
 * @author 马晓亮
 * @version 2019-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ctyd/ctyd")
public class CtydController extends BaseController {

	@Autowired
	private CtydService ctydService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Ctyd get(String id, boolean isNewRecord) {
		return ctydService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("ctyd:ctyd:view")
	@RequestMapping(value = {"list", ""})
	public String list(Ctyd ctyd, Model model) {
		model.addAttribute("ctyd", ctyd);
		return "modules/bright/ctyd/ctydList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ctyd:ctyd:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Ctyd> listData(Ctyd ctyd, HttpServletRequest request, HttpServletResponse response) {
		ctyd.setPage(new Page<>(request, response));
		Page<Ctyd> page = ctydService.findPage(ctyd);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ctyd:ctyd:view")
	@RequestMapping(value = "form")
	public String form(Ctyd ctyd, Model model) {
		model.addAttribute("ctyd", ctyd);
		return "modules/bright/ctyd/ctydForm";
	}

	/**
	 * 保存餐厅预订表
	 */
	@RequiresPermissions("ctyd:ctyd:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Ctyd ctyd) {
		ctydService.save(ctyd);
		return renderResult(Global.TRUE, text("保存餐厅预订表成功！"));
	}
	
	/**
	 * 删除餐厅预订表
	 */
	@RequiresPermissions("ctyd:ctyd:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Ctyd ctyd) {
		ctydService.delete(ctyd);
		return renderResult(Global.TRUE, text("删除餐厅预订表成功！"));
	}

	/**
	 *  修改餐厅预订状态
	 * @param ctyd
	 * @return
	 */
	@RequestMapping(value = "updateCtyd")
	@ResponseBody
	public String updateCtyd(Ctyd ctyd) {
		Ctyd ctyd1 = ctydService.get(ctyd.getId());
		//取消预订订单
		if (ctyd.getZt() == Ctyd.CTYD_ZT_YQX){
			if (ctyd1.getZt() > Ctyd.CTYD_ZT_YQX){
				return renderResult(Global.TRUE, text("订单已确认，无法取消！"));
			}
			ctydService.updateZt(ctyd.getId(), Ctyd.CTYD_ZT_YQX);
			return renderResult(Global.TRUE, text("取消餐厅预订成功！"));
		}
		//确定订单
		if (ctyd.getZt() == Ctyd.CTYD_ZT_YQR){
			if (ctyd1.getZt() == Ctyd.CTYD_ZT_YQX){
				return renderResult(Global.TRUE, text("该订单已取消，无法确认订单！"));
			}
			ctydService.updateZt(ctyd.getId(), Ctyd.CTYD_ZT_YQR);
			return renderResult(Global.TRUE, text("确定订单预订成功！"));
		}
		//已用餐
		if (ctyd.getZt() == Ctyd.CTYD_ZT_YYC){
			ctydService.updateZt(ctyd.getId(), Ctyd.CTYD_ZT_YYC);
			return renderResult(Global.TRUE, text("确认用餐成功！"));
		}
		return renderResult(Global.TRUE, text("更新餐厅预订失败！"));
	}
}