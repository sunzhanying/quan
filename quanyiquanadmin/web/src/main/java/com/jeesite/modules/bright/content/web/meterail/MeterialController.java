/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.web.meterail;

import com.google.common.base.Strings;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.content.dao.meterail.visited.MeterialVisitedLogDao;
import com.jeesite.modules.bright.content.entity.meterail.Meterial;
import com.jeesite.modules.bright.content.entity.meterail.MeterialDetail;
import com.jeesite.modules.bright.content.entity.meterail.channel.MeterialChannel;
import com.jeesite.modules.bright.content.service.meterail.MeterialService;
import com.jeesite.modules.bright.setfocus.entity.meterialtype.MeterialType;
import com.jeesite.modules.bright.setfocus.entity.others.externallink.MeterialExternalLink;
import com.jeesite.modules.bright.setfocus.entity.others.salesroom.Salesroom;
import com.jeesite.modules.bright.setfocus.entity.source.Source;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import com.jeesite.modules.bright.setfocus.service.meterialtype.MeterialTypeService;
import com.jeesite.modules.bright.setfocus.service.others.externallink.MeterialExternalLinkService;
import com.jeesite.modules.bright.setfocus.service.others.salesroom.SalesroomService;
import com.jeesite.modules.bright.setfocus.service.source.SourceService;
import com.jeesite.modules.bright.setfocus.service.tag.TagService;
import com.jeesite.modules.bright.util.QRCodeUtil;
import com.jeesite.modules.bright.util.message.Message;
import com.jeesite.modules.bright.util.message.MessageEnum;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 素材表Controller
 * @author liqingfeng
 * @version 2019-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/content/meterail/meterial")
public class MeterialController extends BaseController {

	@Autowired
	private MeterialService meterialService;
	@Autowired
	private TagService tagService;
	@Autowired
	private MeterialTypeService meterialTypeService;

	@Autowired
	private SourceService sourceService;

	@Autowired
	private SalesroomService salesroomService;
	@Autowired
	private MeterialExternalLinkService meterialExternalLinkService;
	@Autowired
	private MeterialVisitedLogDao meterialVisitedLogDao;

	@Value("${qrCode_realm_name}")
	protected String qrCodeRealmName;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Meterial get(String id, boolean isNewRecord) {
		Meterial meterial = meterialService.get(id, isNewRecord);
		String sb  = "";
		for (int i = 0; i < meterial.getMeterialDetailList().size(); i++) {
			if(i!=0){
				sb += "|";
			}
			sb += (meterial.getMeterialDetailList()).get(i).getContentUrl();
		}
		meterial.setUpload(sb);
		return meterial;
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("content:meterail:meterial:view")
	@RequestMapping(value = {"list", ""})
	public String list(Meterial meterial, Model model) {
		model.addAttribute("meterial", meterial);
		model.addAttribute("tagList",tagService.findList(new Tag()));
		return "modules/bright/content/meterail/meterialList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("content:meterail:meterial:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Meterial> listData(Meterial meterial, HttpServletRequest request, HttpServletResponse response) {
		meterial.setPage(new Page<>(request, response));
		Page<Meterial> page = meterialService.findPage(meterial);
		for (Meterial m:page.getList()
			 ) {
			String name ="";
			MeterialType t = meterialTypeService.get(m.getType());
			for (int i = 0;i>=0;i++) {
				if(t.getParentCode().equals("0")){
					name = t.getName();
					break;
				}
				t = meterialTypeService.get(t.getParentCode());
			}
			m.setType(name);
			m.setTagList(getTag(m.getTagsId()));
			m.setPersonTime(meterialVisitedLogDao.getPersonTime(m.getId()));
			m.setVisitor(meterialVisitedLogDao.getVisitor(m.getId()));
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("content:meterail:meterial:view")
	@RequestMapping(value = "form")
	public String form(Meterial meterial,MeterialChannel meterialChannel, Model model) {
		if(Strings.isNullOrEmpty(meterial.getLinkType())){
			meterial.setLinkType(Meterial.LINK_INTERNAL);
		}
		if(Strings.isNullOrEmpty(meterial.getMaterialStatus())){
			meterial.setMaterialStatus(Meterial.STATUS_PUBLISH);
		}
		List<Salesroom> salesroomsList = salesroomService.findList(new Salesroom());
		Collections.sort(salesroomsList,new sortByDefault()); //默认的放前面
		Salesroom salesroom = salesroomsList.get(0);
		salesroom.setName(salesroom.getName()+"【默认】");

		model.addAttribute("salesroomList",salesroomsList);
		model.addAttribute("meterial", meterial);
		model.addAttribute("tagList",tagService.findList(new Tag()));
        model.addAttribute("meterialDetailList",new ArrayList<MeterialDetail>());
        model.addAttribute("meterialExternalLinkList",meterialExternalLinkService.findList(new MeterialExternalLink()));
        return "modules/bright/content/meterail/meterialForm";
	}

	/**
	 * 传播你的内容页面
	 * @param meterial
	 * @param meterialChannel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "propagate/spread")
	public String spread(Meterial meterial, MeterialChannel meterialChannel,Model model) {
		if(!StringUtil.isBlank(meterial.getId())) meterial = meterialService.get(meterial.getId());
		model.addAttribute("meterial", meterial);
		model.addAttribute("meterialChannel", meterialChannel);
		model.addAttribute("urlSourceList",sourceService.findList(new Source()));
		return "modules/bright/content/meterail/propagate/spread";
	}

	/**
	 * 流量分析
	 * @param meterial
	 * @param meterialChannel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "propagate/flow")
	public String flow(Meterial meterial,MeterialChannel meterialChannel, Model model) {
		if(!StringUtil.isBlank(meterial.getId())) meterial = meterialService.get(meterial.getId());
		model.addAttribute("meterial", meterial);
		return "modules/bright/content/meterail/propagate/flow";
	}
	/**
	 * 保存素材表
	 */
	@RequiresPermissions("content:meterail:meterial:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Meterial meterial,@RequestParam String upload,Model model) {
		//图片
		String [] uploads = upload.split("\\|");
		List<MeterialDetail> details = new ArrayList<>();
		for (int i = 0; i < uploads.length; i++) {
			MeterialDetail detail2 = new MeterialDetail();
			detail2.setMeterialId(meterial);
			detail2.setAttributeName(meterial.getAttributeName());
			detail2.setContentUrl(uploads[i]);
			details.add(detail2);
		}
		meterial.setMeterialDetailList(details);
		//标签
		String[] tags = (meterial.getTagsId()).split("\\,");
		if(tags.length>3){
			return renderResult(Global.FALSE, text("最多只能选择3个标签！"));
		}
		meterialService.save(meterial);
		model.addAttribute("meterial", meterial);
		model.addAttribute("tagList",tagService.findList(new Tag()));
		return renderResult(Global.TRUE, text("保存素材表成功！"));
		//return "modules/bright/content/meterail/meterialList";
	}
	
	/**
	 * 删除素材表
	 */
	@RequiresPermissions("content:meterail:meterial:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Meterial meterial) {
		meterialService.delete(meterial);
		return renderResult(Global.TRUE, text("删除素材表成功！"));
	}

	private List<Tag> getTag(String str){
		String [] arr = str.split(",");
		List<Tag> tags = new ArrayList<>();
		for (String s:arr
		) {
			Tag tag = tagService.get(s);
			tags.add(tag);
		}
		return tags;
	}



	/**
	 * 获取分类树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */

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
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getId(), e.getName()));
			mapList.add(map);
		}
		return mapList;
	}

	//生成二维码
	@RequestMapping(value = "qrCode",method = RequestMethod.POST)
	public @ResponseBody
	Message qrCode(Meterial meterial, Message message) throws Exception{
		String url = "";
		try {
			String text = qrCodeRealmName + "/meterials/?url=/pages/home/recipeDetail/recipeDetail&id="+meterial.getId();
			url =  QRCodeUtil.qrCode(text,"meterials",meterial.getId());
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
		message.setDatas(result+meterial.getId()+".jpg");
		MessageEnum.Success.toMessage(message);
		return message;
	}

}
