/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.web.micro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bright.util.Code;
import com.jeesite.modules.bright.util.QRCodeUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.content.entity.micro.MicroPage;
import com.jeesite.modules.bright.content.service.micro.MicroPageService;

/**
 * 微页面Controller
 * @author liqingfeng
 * @version 2019-06-18
 */
@Controller
@RequestMapping(value = "${adminPath}/content/micro/microPage")
public class MicroPageController extends BaseController {

	@Autowired
	private MicroPageService microPageService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MicroPage get(String id, boolean isNewRecord) {
		return microPageService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("content:micro:microPage:view")
	@RequestMapping(value = {"list", ""})
	public String list(MicroPage microPage, Model model) {
		model.addAttribute("microPage", microPage);
		return "modules/bright/content/micro/microPageList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("content:micro:microPage:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<MicroPage> listData(MicroPage microPage, HttpServletRequest request, HttpServletResponse response) {
		microPage.setPage(new Page<>(request, response));
		Page<MicroPage> page = microPageService.findPage(microPage);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("content:micro:microPage:view")
	@RequestMapping(value = "form")
	public String form(MicroPage microPage, Model model) {
		model.addAttribute("microPage", microPage);
		return "modules/bright/content/micro/microPageForm";
	}

	/**
	 * 保存
	 */
	@RequiresPermissions("content:micro:microPage:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated MicroPage microPage) {
		microPageService.save(microPage);
		return renderResult(Global.TRUE, text("保存成功！"));
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("content:micro:microPage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MicroPage microPage) {
		microPageService.delete(microPage);
		return renderResult(Global.TRUE, text("删除成功！"));
	}


	//生成二维码
	@RequestMapping(value = "qrCode",method = RequestMethod.POST)
	@ResponseBody
	public String qrCode(MicroPage microPage, Model model) throws Exception{
		String url = "";
		try {
			String text = microPage.getId();
			url =  QRCodeUtil.qrCode(text,"goodsbag",microPage.getId());
			microPage.setPopularize("/brandhub/userfiles/bar/userfiles/barcode/goodsbag/"+microPage.getId()+".jpg");
			microPageService.save(microPage);
		}catch (Exception e){
			return (Code.API_QRCODE_ERROR).getMessage();
		}
		System.out.println("url=========="+url);
		return "/barcode/goodsbag/"+microPage.getId()+".jpg";
	}
}