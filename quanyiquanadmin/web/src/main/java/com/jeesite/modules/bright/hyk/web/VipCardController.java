/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.hyk.web;

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
import com.jeesite.modules.bright.hyk.entity.VipCard;
import com.jeesite.modules.bright.hyk.service.VipCardService;

/**
 * 会员卡Controller
 * @author 马晓亮
 * @version 2019-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/hyk/vipCard")
public class VipCardController extends BaseController {

	@Autowired
	private VipCardService vipCardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public VipCard get(String id, boolean isNewRecord) {
		return vipCardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("hyk:vipCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipCard vipCard, Model model) {
		model.addAttribute("vipCard", vipCard);
		return "modules/bright/hyk/vipCardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("hyk:vipCard:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<VipCard> listData(VipCard vipCard, HttpServletRequest request, HttpServletResponse response) {
		vipCard.setPage(new Page<>(request, response));
		Page<VipCard> page = vipCardService.findPage(vipCard);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("hyk:vipCard:view")
	@RequestMapping(value = "form")
	public String form(VipCard vipCard, Model model) {
		if (vipCard.getIsNewRecord()) {
			vipCard.setSxj("2");
		}
		model.addAttribute("vipCard", vipCard);
		return "modules/bright/hyk/vipCardForm";
	}

	/**
	 * 保存会员卡
	 */
	@RequiresPermissions("hyk:vipCard:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated VipCard vipCard) {
		vipCardService.save(vipCard);
		return renderResult(Global.TRUE, text("保存会员卡成功！"));
	}
	
	/**
	 * 删除会员卡
	 */
	@RequiresPermissions("hyk:vipCard:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(VipCard vipCard) {
		vipCardService.delete(vipCard);
		return renderResult(Global.TRUE, text("删除会员卡成功！"));
	}

	/**
	 * 上下架会员卡
	 * @param vipCard
	 * @return
	 */
	@RequestMapping(value = "updateCardSxj")
	@ResponseBody
	public String updateCardSxj(VipCard vipCard) {
		vipCardService.updateSxj(vipCard.getSxj(), vipCard.getId());
		if (VipCard.VIP_CARD_SJ.equals(vipCard.getSxj())){//上架
			return renderResult(Global.TRUE, text("会员卡上架成功！"));
		}else{//下架
			return renderResult(Global.TRUE, text("会员卡下架成功！"));
		}
		//return renderResult(Global.TRUE, text("会员卡上下架失败！"));
	}
	
}