/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.web.meterialtype;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jeesite.modules.bright.content.entity.meterail.Meterial;
import com.jeesite.modules.bright.content.service.meterail.MeterialService;
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
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.setfocus.entity.meterialtype.MeterialType;
import com.jeesite.modules.bright.setfocus.service.meterialtype.MeterialTypeService;

/**
 * 产品类型Controller
 * @author liqingfeng
 * @version 2019-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/setfocus/meterialtype/meterialType")
public class MeterialTypeController extends BaseController {

	@Autowired
	private MeterialTypeService meterialTypeService;
	@Autowired
	private MeterialService meterialService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MeterialType get(String code, boolean isNewRecord) {
		return meterialTypeService.get(code, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:view")
	@RequestMapping(value = {"list", ""})
	public String list(MeterialType meterialType, Model model) {
		model.addAttribute("meterialType", meterialType);
		return "modules/bright/setfocus/meterialtype/meterialTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<MeterialType> listData(MeterialType meterialType) {
		if (StringUtils.isBlank(meterialType.getParentCode())) {
			meterialType.setParentCode(MeterialType.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(meterialType.getName())){
			meterialType.setParentCode(null);
		}
		if (StringUtils.isNotBlank(meterialType.getType())){
			meterialType.setParentCode(null);
		}
		if (StringUtils.isNotBlank(meterialType.getRemarks())){
			meterialType.setParentCode(null);
		}
		List<MeterialType> list = meterialTypeService.findList(meterialType);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:view")
	@RequestMapping(value = "form")
	public String form(MeterialType meterialType, Model model) {
		// 创建并初始化下一个节点信息
		meterialType = createNextNode(meterialType);
		model.addAttribute("meterialType", meterialType);
		return "modules/bright/setfocus/meterialtype/meterialTypeForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public MeterialType createNextNode(MeterialType meterialType) {
		if (StringUtils.isNotBlank(meterialType.getParentCode())){
			meterialType.setParent(meterialTypeService.get(meterialType.getParentCode()));
		}
		if (meterialType.getIsNewRecord()) {
			MeterialType where = new MeterialType();
			where.setParentCode(meterialType.getParentCode());
			MeterialType last = meterialTypeService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				meterialType.setTreeSort(last.getTreeSort() + 30);
				meterialType.setCode(IdGen.nextCode(last.getCode()));
			}else if (meterialType.getParent() != null){
				meterialType.setCode(meterialType.getParent().getCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (meterialType.getTreeSort() == null){
			meterialType.setTreeSort(MeterialType.DEFAULT_TREE_SORT);
		}
		return meterialType;
	}

	/**
	 * 保存产品类型
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated MeterialType meterialType) {
		meterialTypeService.save(meterialType);
		return renderResult(Global.TRUE, text("保存产品类型成功！"));
	}
	
	/**
	 * 删除产品类型
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MeterialType meterialType) {
		PageInfo<Meterial> pageInfo  = meterialService.findAllList(1,1,meterialType.getCode(),null,null,null);
		if(pageInfo.getList().size()>0){
			return renderResult(Global.FALSE, text("存在该产品类型及子类型的素材,不能删除"));
		}else {
			meterialTypeService.delete(meterialType);
			return renderResult(Global.TRUE, text("删除产品类型成功！"));
		}

	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<MeterialType> list = meterialTypeService.findList(new MeterialType());
		for (int i=0; i<list.size(); i++){
			MeterialType e = list.get(i);
			// 过滤非正常的数据
			if (!MeterialType.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getCode(), e.getName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("setfocus:meterialtype:meterialType:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(MeterialType meterialType){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		meterialTypeService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	
}