/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import com.jeesite.modules.bright.setfocus.service.tag.TagService;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.entity.sptype.SpType;
import com.jeesite.modules.bright.sp.entity.yhq.SpYhq;
import com.jeesite.modules.bright.sp.service.SpXxService;
import com.jeesite.modules.bright.sp.service.sptype.SpTypeService;
import com.jeesite.modules.bright.sp.service.yhq.SpYhqService;
import com.jeesite.modules.bright.util.QRCodeUtil;
import com.jeesite.modules.bright.util.message.Message;
import com.jeesite.modules.bright.util.message.MessageEnum;
import com.jeesite.modules.qyjg.entity.Qyjg;
import com.jeesite.modules.qyjg.service.QyjgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权益券信息Controller
 * @author 马晓亮
 * @version 2019-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sp/spXx")
public class SpXxController extends BaseController {

	@Autowired
	private SpXxService spXxService;
	@Autowired
	private SpTypeService spTypeService;
	@Autowired
	private SpYhqService spYhqService;
	@Autowired
	private TagService tagService;
	@Autowired
	private QyjgService qyjgService;

	@Value("${qrCode_realm_name}")
	protected String qrCodeRealmName;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SpXx get(String id, boolean isNewRecord) {
		return spXxService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sp:spXx:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpXx spXx, Model model) {
		model.addAttribute("spXx", spXx);
		model.addAttribute("spTypeList", spTypeService.findList(new SpType()));
		return "modules/bright/sp/spXxList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sp:spXx:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SpXx> listData(SpXx spXx, HttpServletRequest request, HttpServletResponse response) {
		spXx.setPage(new Page<>(request, response));
		Page<SpXx> page = spXxService.findPage(spXx);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sp:spXx:view")
	@RequestMapping(value = "form")
	public String form(SpXx spXx, Model model) {
		if (spXx.getIsNewRecord()) {
			spXx.setIshyqy("2");
			spXx.setJldw("1");
			spXx.setSpSxlx("1");
			spXx.setUseYhq("0");
		}
		model.addAttribute("spXx", spXx);
		model.addAttribute("spTypeList", spTypeService.findList(new SpType()));
		List<Map<String,String>> spTypeList = spTypeService.findTwoSpList("");
		model.addAttribute("spTypeListTwo", spTypeList);
		model.addAttribute("yhqList", spYhqService.findList(new SpYhq()));
		model.addAttribute("labelList", tagService.findList(new Tag()));

		//新增 输入类型 校验
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,String> map1 = new HashMap<>();
		map1.put("name","英文");
		map1.put("val","1");
		list.add(map1);

		Map<String,String> map2 = new HashMap<>();
		map2.put("name","数字");
		map2.put("val","2");
		list.add(map2);

		Map<String,String> map3 = new HashMap<>();
		map3.put("name","英文+数字");
		map3.put("val","3");
		list.add(map3);

		Map<String,String> map4 = new HashMap<>();
		map4.put("name","全文本");
		map4.put("val","4");
		list.add(map4);

		/*Map<String,String> map5 = new HashMap<>();
		map5.put("name","英文小写+数字");
		map5.put("val","5");
		list.add(map5);

		Map<String,String> map6 = new HashMap<>();
		map6.put("name","纯文本");
		map6.put("val","6");
		list.add(map6);*/

		model.addAttribute("typeCheck", list);


		List<Map<String,String>> listIncome = new ArrayList<>();
		Map<String,String> mapIncome1 = new HashMap<>();
		mapIncome1.put("name","固定金额");
		mapIncome1.put("val","1");
		listIncome.add(mapIncome1);

		/*Map<String,String> mapIncome2 = new HashMap<>();
		mapIncome2.put("name","固定比例");
		mapIncome2.put("val","2");
		listIncome.add(mapIncome2);
		model.addAttribute("incomeTypeSelect", listIncome);*/
		return "modules/bright/sp/spXxForm";
	}

	/**
	 * 保存权益券信息
	 */
	@RequiresPermissions("sp:spXx:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SpXx spXx) {
		spXx.setStatus(SpXx.STATUS_DISABLE);
		spXxService.save(spXx);
		return renderResult(Global.TRUE, text("保存权益券信息成功！"));
	}
	
	/**
	 * 删除权益券信息
	 */
	@RequiresPermissions("sp:spXx:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SpXx spXx) {
		spXxService.delete(spXx);
		return renderResult(Global.TRUE, text("删除权益券信息成功！"));
	}


	/**
	 * 停用权益券信息
	 */
	@RequiresPermissions("sp:spXx:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(SpXx spXx) {
		spXx.setStatus(SpXx.STATUS_DISABLE);
		spXxService.updateStatus(spXx);
		return renderResult(Global.TRUE, text("停用权益券成功"));
	}

	/**
	 * 启用权益券信息
	 */
	@RequiresPermissions("sp:spXx:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(SpXx spXx) {
		//判断券是否有价格
		Qyjg qyjg = new Qyjg();
		qyjg.setQyqId(spXx.getId());
		if (qyjgService.findCount(qyjg) == 0){
			return renderResult(Global.FALSE, text("请先为该券设置价格"));
		}
		spXx.setStatus(SpXx.STATUS_NORMAL);
		spXxService.updateStatus(spXx);
		return renderResult(Global.TRUE, text("启用权益券成功"));
	}

	//生成二维码
	@RequestMapping(value = "qrCode",method = RequestMethod.POST)
	public @ResponseBody Message qrCode(SpXx spXx, Message message) throws Exception{
		String url = "";
		try {
			String text = qrCodeRealmName + "/goods?url=/pages/wine/wineDetail/wineDetail&id="+spXx.getId();
			url =  QRCodeUtil.qrCode(text,"goods",spXx.getId());
		}catch (Exception e){
			MessageEnum.QRCODE_ERR.toMessage(message);
			return message;
		}
		//获得第一个点的位置
		int index=url.indexOf("/");
		System.out.println(index);
		//根据第一个点的位置 获得第二个点的位置
		index=url.indexOf("/", index+1);
		//根据第二个点的位置，截取 字符串。得到结果 result
		String result=url.substring(index);
		message.setDatas(result+spXx.getId()+".jpg");
		MessageEnum.Success.toMessage(message);
		return message;
	}
}