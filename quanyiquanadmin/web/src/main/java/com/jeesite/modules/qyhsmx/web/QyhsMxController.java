/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhsmx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.API.util.excelUtil.ExcelUtils;
import com.jeesite.common.mybatis.mapper.MapperHelper;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.service.SpXxService;
import com.jeesite.modules.bright.t.service.khxx.KhXxService;
import com.jeesite.modules.order.entity.OrderMx;
import com.jeesite.modules.order.service.OrderMxService;
import com.jeesite.modules.qyhs.service.QyhsService;
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
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyhsmx.service.QyhsMxService;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 权益回收明细Controller
 * @author 马晓亮
 * @version 2020-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/qyhsmx/qyhsMx")
public class QyhsMxController extends BaseController {

	@Autowired
	private QyhsMxService qyhsMxService;
	@Autowired
	private KhXxService khXxService;
	@Autowired
	private SpXxService spXxService;
	@Autowired
	private QyhsService qyhsService;
	@Autowired
	private OrderMxService orderMxService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public QyhsMx get(String id, boolean isNewRecord) {
		return qyhsMxService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	/*@RequiresPermissions("qyhsmx:qyhsMx:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(QyhsMx qyhsMx, Model model) {
		/*qyhsMx.setKhXx(khXxService.get(qyhsMx.getKhid()));
		qyhsMx.setSpXx(spXxService.get(qyhsMx.getQyqId()));
		qyhsMx.setQyhs(qyhsService.get(qyhsMx.getQyhsId()));
		model.addAttribute("qyhsMx", qyhsMx);*/
		//显示所有券
		model.addAttribute("spList", spXxService.findList(new SpXx()));
		//待审核
		if ("1".equals(qyhsMx.getZt())){
			return "modules/qyhsmx/qyhsMxList";
		}else { //已审核
			return "modules/qyhsmx/qyhsMxList1";
		}
	}

	@RequestMapping(value = "kclist")
	public String kclist(QyhsMx qyhsMx, Model model) {
		model.addAttribute("qyhsMx", qyhsMx);
		return "modules/qyhsmx/qyhsMxKcList";
	}

	/**
	 * 查询列表数据
	 */
	/*@RequiresPermissions("qyhsmx:qyhsMx:view")*/
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<QyhsMx> listData(QyhsMx qyhsMx, HttpServletRequest request, HttpServletResponse response) {
		if ("2".equals(qyhsMx.getZt())){
			qyhsMx.setZt("");
			qyhsMx.getSqlMap().getWhere().and("zt", QueryType.GTE, "2");
		}
		qyhsMx.setPage(new Page<>(request, response));
		Page<QyhsMx> page = qyhsMxService.findPage(qyhsMx);
		return page;
	}

	@RequestMapping(value = "kclistData")
	@ResponseBody
	public Page<QyhsMx> kclistData(QyhsMx qyhsMx, HttpServletRequest request, HttpServletResponse response) {
		String extColumn = "count(a.khid) AS \"count\"";
		qyhsMx.getSqlMap().add("extColumn", extColumn);
		qyhsMx.getSqlMap().getWhere().and("zt", QueryType.GT, QyhsMx.STATUS_TH);
		qyhsMx.getSqlMap().add("extWhere", "GROUP BY a.khid");
		qyhsMx.setPage(new Page<>(request, response));
		Page<QyhsMx> page = qyhsMxService.findPage(qyhsMx);
		page.getList().forEach(item ->{
			QyhsMx qyhsMx1 = new QyhsMx();
			qyhsMx1.setKhid(item.getKhid());
			qyhsMx1.setQyqId(item.getQyqId());
			//剩余库存
			qyhsMx1.setZt(QyhsMx.STATUS_CSZ);
			item.setSykc(qyhsMxService.findCount(qyhsMx1));
			//已出售数
			qyhsMx1.setZt("");
			qyhsMx1.getSqlMap().getWhere().and("zt", QueryType.GT, QyhsMx.STATUS_CSZ);
			item.setYcs(qyhsMxService.findCount(qyhsMx1));
		});
		return page;
	}

	//订单列表
	@RequestMapping(value = "orderListData")
	@ResponseBody
	public Page<QyhsMx> orderListData(QyhsMx qyhsMx, HttpServletRequest request, HttpServletResponse response) {
		//订单明细
		OrderMx orderMx = new OrderMx();
		orderMx.setOrderId(qyhsMx.getOrderId());
		List<OrderMx> orderMxList = orderMxService.findList(orderMx);
		List<String> stringList = new ArrayList<>();
		orderMxList.forEach(item ->{
			stringList.add(item.getQymxId());
		});
		qyhsMx.setOrderId("");
		qyhsMx.getSqlMap().getWhere().and("id", QueryType.IN, stringList.toArray());
		String extColumn = "count(a.khid) AS \"count\"";
		qyhsMx.getSqlMap().add("extColumn", extColumn);
		qyhsMx.getSqlMap().add("extWhere", "GROUP BY a.khid");
		qyhsMx.setPage(new Page<>(request, response));
		Page<QyhsMx> page = qyhsMxService.findPage(qyhsMx);
		return page;
	}

	//提现申请中的明细
	@RequestMapping(value = "qyhsMxTxList")
	@ResponseBody
	public Page<QyhsMx> qyhsMxTxList(QyhsMx qyhsMx, HttpServletRequest request, HttpServletResponse response) {
		qyhsMx.setPage(new Page<>(request, response));
		Page<QyhsMx> page = qyhsMxService.findPage(qyhsMx);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("qyhsmx:qyhsMx:view")
	@RequestMapping(value = "form")
	public String form(QyhsMx qyhsMx, Model model) {
		model.addAttribute("qyhsMx", qyhsMx);
		return "modules/qyhsmx/qyhsMxForm";
	}

	/**
	 * 保存权益回收明细
	 */
	@RequiresPermissions("qyhsmx:qyhsMx:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated QyhsMx qyhsMx) {
		qyhsMxService.save(qyhsMx);
		return renderResult(Global.TRUE, text("保存权益回收明细成功！"));
	}

	/**
	 * 通过权益券明细
	 * @param qyhsMx
	 * @return
	 */
	@RequestMapping(value = "updateTg")
	@ResponseBody
	public String updateTg(QyhsMx qyhsMx) {
		qyhsMx.setZt(QyhsMx.STATUS_CSZ);
		qyhsMxService.update(qyhsMx);
		return renderResult(Global.TRUE, text("券已成功通过！"));
	}

	/**
	 * 退回权益券明细
	 * @param qyhsMx
	 * @return
	 */
	@RequestMapping(value = "updateTh")
	@ResponseBody
	public String updateTh(QyhsMx qyhsMx) {
		qyhsMx.setZt(QyhsMx.STATUS_TH);
		qyhsMxService.update(qyhsMx);
		return renderResult(Global.TRUE, text("券已成功退回！"));
	}

	/**
	 * 批量通过或退回
	 * type 1 通过 2 退回
	 * @return
	 */
	@RequestMapping(value = "updateTgOrTh")
	@ResponseBody
	public String updateTgOrTh(String str, String type) {
		//System.out.println("type: " + type + "\tstr:" + str);
		if("22".equals(type)){
			qyhsMxService.updateTgOrTh(str, "2");
		}else{
			qyhsMxService.updateTgOrTh(str, type);
		}

		if ("1".equals(type)){
			return renderResult(Global.TRUE, text("券已成功批量通过！"));
		}else if("22".equals(type)){
			return renderResult(Global.TRUE, text("券已成功批量退回！"));
		}else{
			return renderResult(Global.TRUE, text("券已成功批量退回！"));
		}
	}


	/**
	 * 删除权益回收明细
	 */
	@RequiresPermissions("qyhsmx:qyhsMx:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(QyhsMx qyhsMx) {
		qyhsMxService.delete(qyhsMx);
		return renderResult(Global.TRUE, text("删除权益回收明细成功！"));
	}

	/**
	 * 批量下载
	 * @return
	 */
	@RequestMapping(value = "downloadQuan")
	@ResponseBody
	public void downloadQuan(String str, HttpServletRequest request,HttpServletResponse response) {
		//System.out.println("type: " + type + "\tstr:" + str);
		if(str == null || "".equals(str)){
			//return renderResult(Global.TRUE, text("无下载内容！"));
		}
		String arr[] = str.split(",");
		List<QyhsMx> list = new ArrayList<>();

		QyhsMx qyhsMx = new QyhsMx();
		qyhsMx.setPage(new Page<>(request, response));
		for(String s : arr){

			qyhsMx.setId(s);
			Page<QyhsMx> page = qyhsMxService.findPage(qyhsMx);
			List<QyhsMx> listTemp = page.getList();
			//List<QyhsMx> listTemp = qyhsMxService.downloadFile(s);
			list.addAll(listTemp);
		}

		if(list == null || list.isEmpty()){
			//return renderResult(Global.TRUE, text("无下载内容！"));
		}
		try{
			System.out.println("in downloadExcel");
			response.reset();//貌似有用
			String name = "卡券.xlsx";
			String[] title = {"状态","类型","券标题","卡号","卡密","卡图","有效期","出售人头像","手机号","出售单号","出售时间"};

			List<Object[]> objects = new ArrayList<>();
			objects.add(title);
			for(QyhsMx temp : list){
				String statusTemp = "已审核";

				String lx = "";
				if("1".equals(temp.getType())){
					lx = "卡密";
				}else if("2".equals(temp.getType())){
					lx = "卡号/卡密";
				}else if("3".equals(temp.getType())){
					lx = "图片截图";
				}

				String imageUrlTemp = "";
				if(temp.getImg() != null && !"".equals(temp.getImg())){
					imageUrlTemp = "https://quan.whaleupgo.com" + temp.getImg();
				}

				//有效期
				SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
				String yxq = "";
				if(temp.getYxqDate() != null){
					yxq = sdfInput.format(temp.getYxqDate());
				}

				String csTime = "";
				if(temp.getCreateDate() != null){
					csTime = sdfInput.format(temp.getCreateDate());
				}

				String[] tempArr = {statusTemp,lx,temp.getSpXx().getSpmc(),temp.getKh(),temp.getKm(),imageUrlTemp
				,yxq,temp.getKhXx().getWxtx(),temp.getKhXx().getSj(),temp.getQyhsId(),csTime};

				objects.add(tempArr);
			}
			response.setContentType("application/x-excel;charset=UTF-8");
			response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(name,"UTF-8"));
			response.setCharacterEncoding("UTF-8");
			// response.getOutputStream() 也可以指向固定目录的文件流
			ExcelUtils.exportExcelToResponse(name,objects,"sheet007",response.getOutputStream());

		}catch (Exception e){
			//return renderResult(Global.TRUE, text("下载失败！"));
		}
		//return renderResult(Global.TRUE, text("下载成功！"));
	}

}