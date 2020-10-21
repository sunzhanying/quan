/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.API.service.Response;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
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
import com.jeesite.modules.txsh.entity.Txsh;
import com.jeesite.modules.txsh.service.TxshService;

/**
 * 提现审核Controller
 * @author 马晓亮
 * @version 2020-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/txsh/txsh")
public class TxshController extends BaseController {

	@Autowired
	private TxshService txshService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Txsh get(String id, boolean isNewRecord) {
		return txshService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = {"list", ""})
	public String list(Txsh txsh, Model model) {
		model.addAttribute("txsh", txsh);
		return "modules/txsh/txshList";
	}

	/**
	 * 到提现页面
	 * @param qyhsMx
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "qyhsMxTxList")
	public String list(QyhsMx qyhsMx, Model model) {
		model.addAttribute("qyhsMx", qyhsMx);
		return "modules/txsh/qyhsMxTxList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Txsh> listData(Txsh txsh, HttpServletRequest request, HttpServletResponse response) {
		txsh.setPage(new Page<>(request, response));
		Page<Txsh> page = txshService.findPage(txsh);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = "form")
	public String form(Txsh txsh, Model model) {
		model.addAttribute("txsh", txsh);
		return "modules/txsh/txshForm";
	}

	/**
	 * 保存体现审核
	 */
	@RequiresPermissions("txsh:txsh:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Txsh txsh) {
		txshService.save(txsh);
		return renderResult(Global.TRUE, text("保存体现审核成功！"));
	}
	
	/**
	 * 删除体现审核
	 */
	@RequiresPermissions("txsh:txsh:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Txsh txsh) {
		txshService.delete(txsh);
		return renderResult(Global.TRUE, text("删除体现审核成功！"));
	}

	/**
	 * 提现申请
	 * @param txsh
	 * @return
	 */
	/*@RequiresPermissions("txsh:txsh:edit")
	@RequestMapping(value = "tssq")
	@ResponseBody
	public synchronized String tssq(Txsh txsh) {
		if (Txsh.TX_STATUS_TG.equals(txsh.getZt())){
			return renderResult(Global.TRUE, text("不可以重复审核！"));
		}
		Response response = txshService.txsh(txsh);
		if (response.getCode() == 200){
			return renderResult(Global.TRUE, text("审核成功！"));
		}
		return renderResult(Global.TRUE, text(response.getMessage()));
	}*/
}