/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.web.tag;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.content.entity.meterail.Meterial;
import com.jeesite.modules.bright.content.service.meterail.MeterialService;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import com.jeesite.modules.bright.setfocus.service.tag.TagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 标签Controller
 * @author liqingfeng
 * @version 2019-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/setfocus/tag/tag")
public class TagController extends BaseController {

	@Autowired
	private TagService tagService;
	@Autowired
	private MeterialService meterialService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Tag get(String id, boolean isNewRecord) {
		return tagService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("setfocus:tag:tag:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tag tag, Model model) {
		model.addAttribute("tag", tag);
		return "modules/bright/setfocus/tag/tagList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("setfocus:tag:tag:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Tag> listData(Tag tag, HttpServletRequest request, HttpServletResponse response) {
		tag.setPage(new Page<>(request, response));
		Page<Tag> page = tagService.findPage(tag);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("setfocus:tag:tag:view")
	@RequestMapping(value = "form")
	public String form(Tag tag, Model model) {
		tag = createNextNode(tag);
		model.addAttribute("tag", tag);
		return "modules/bright/setfocus/tag/tagForm";
	}

	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public Tag createNextNode(Tag tag) {
		if (tag.getIsNewRecord()) {
			Integer last = tagService.getLastBySort();
			if(!"0".equals(last)){
				tag.setSort(last+1);
			}else {
				tag.setSort(0);
			}
		}
		return tag;
	}


	/**
	 * 保存标签
	 */
	@RequiresPermissions("setfocus:tag:tag:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Tag tag) {
		if(tag.getIsNewRecord()){
			List<Tag> list = tagService.getListByTag((tag.getSort()).toString());
			if(list.size()>0){
				return renderResult(Global.FALSE, text("已存在该编码！"));
			}
		}
		tagService.save(tag);
		return renderResult(Global.TRUE, text("保存标签成功！"));
	}
	
	/**
	 * 删除标签
	 */
	@RequiresPermissions("setfocus:tag:tag:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Tag tag) {
		List<Meterial> list = meterialService.findByTag(tag.getId());
		if(list.size()>0){
			return renderResult(Global.FALSE, text("存在该标签类型的素材,不能删除"));
		}else {
			tagService.delete(tag);
			return renderResult(Global.TRUE, text("删除标签成功！"));
		}

	}
	
}