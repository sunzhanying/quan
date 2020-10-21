/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyhsmx.service.QyhsMxService;
import com.jeesite.modules.txsh.entity.Sell;
import com.jeesite.modules.txsh.entity.Txsh;
import com.jeesite.modules.txsh.service.SellService;
import com.jeesite.modules.txsh.service.TxshService;
import com.jeesite.utils.Paper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private QyhsMxService qyhsMxService;

	@Autowired
	private SellService sellService;

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
	 * 查询账单核对列表
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = {"listZd", ""})
	public String listZd(Txsh txsh, Model model) {
		model.addAttribute("txsh", txsh);
		return "modules/txsh/txshListZd";
	}

	/**
	 * 查询账单核对列表
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = {"txshListZd2", ""})
	public String txshListZd2(Txsh txsh, Model model) {
		model.addAttribute("txsh", txsh);
		return "modules/txsh/txshListZd2";
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

	/**
	 * 批量提现审核不通过、根据提现审核主键，查询出提现审核表中订单号，关联更新出卖券计算状态
	 * type 3
	 * @return
	 */
	@RequestMapping(value = "updateTxsh")
	@ResponseBody
	public String updateTgOrTh(String str, String type) {
		//System.out.println("type: " + type + "\tstr:" + str);
		String[] strings = str.split(",");
		//条件
		for (String string : strings) {
			Txsh txshTemp = new Txsh(string);
			txshTemp.setZt(type);
			txshService.update(txshTemp);

			//根据提现审核主键查询出订单号
			Txsh txshTempForOrder = new Txsh(string);
			txshTempForOrder = txshService.get(txshTempForOrder);
			//根据订单号更新卖券表状态
			if(txshTempForOrder == null){
				continue;
			}
			String orderId = txshTempForOrder.getOrderId();
			if(orderId == null || "".equals(orderId)){
				continue;
			}
			QyhsMx qyhsMx = new QyhsMx();
			qyhsMx.setOrderId(orderId);
			List<QyhsMx> list = qyhsMxService.findList(qyhsMx);
			for(QyhsMx qyhsMx1 :list){
				qyhsMx1.setJszt(QyhsMx.STATUS_JS_SHBTG);//审核不通过
				qyhsMxService.update(qyhsMx1);
			}
		}

		return renderResult(Global.TRUE, text("批量不通过操作成功！"));

	}

	/**
	 * 查询账单列表
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = "listPayData")
	@ResponseBody
	public Page<Map<String,String>> listPayData(Txsh txsh, HttpServletRequest request, HttpServletResponse response) {
		Page pageFront = new Page<>(request, response);
		Page<Map<String,String>> page = new Page<Map<String,String>>();
		Map<String,String> param = new HashMap<>();
		if(txsh == null){
			return page;
		}

		param.put("orderId",txsh.getOrderId());
		param.put("id",txsh.getId());
		if(txsh != null && txsh.getKhXx() != null){
			param.put("wxnc",txsh.getKhXx().getWxnc());
		}
		param.put("zt",txsh.getZt());

		param.put("startDate",txsh.getStartDate());
		param.put("endDate",txsh.getEndDate());
		//PageHelper.startPage(pageFront.getPageNo(),pageFront.getPageSize());
		List<Map<String,String>> list = txshService.findPayPage(param);
		Paper<Map<String,String>> paper = new Paper<Map<String,String>>(pageFront.getPageNo(),pageFront.getPageSize(),list);//paper.getDataList()就是子数组数据
		page.setCount(list.size());
		page.setList(paper.getDataList());
		page.setPageNo(pageFront.getPageNo());
		page.setPageSize(pageFront.getPageSize());
		return page;
	}

	/**
	 * 立即提现或者中止
	 * 状态1：结算中，
	 * 状态2：已结算，
	 * 状态3：批量审核不通过（收益扣除）,
	 * 状态4：已中止，
	 * 状态5：程序打款失败
	 * @return
	 */
	@RequestMapping(value = "updatePay")
	@ResponseBody
	public String updatePay(String str, String type) {
		//System.out.println("type: " + type + "\tstr:" + str);
		String[] strings = str.split(",");
		//中止
		if("4".equals(type)){
			for (String string : strings) {
				Txsh txshTemp = new Txsh(string);
				txshTemp.setZt(type);
				txshService.update(txshTemp);
			}
			return renderResult(Global.TRUE, text("批量中止操作成功！"));
		}else if("2".equals(type)){//打款
			boolean boo = true;
			String failIdAndInfo = "";
			for (String string : strings) {
				Txsh txsh = new Txsh();
				txsh.setId(string);
				Txsh txshDb = txshService.get(txsh);
				if("2".equals(txshDb.getZt()) || "3".equals(txshDb.getZt()) || "4".equals(txshDb.getZt())){//结算中1、程序打款失败5、的可再次打款
					boo = false;
					failIdAndInfo = "id为：" + string + " 失败原因：当前状态不能打款";
					break;
				}
				String booResult = txshService.txsh(txshDb);
				if(!"ok".equals(booResult)){//如果有一个失败就中止打款
					boo = false;
					failIdAndInfo = "id为：" + string + " 失败原因：" + booResult;
					break;
				}
			}
			if(boo){
				return renderResult(Global.TRUE, text("已调用商户付款成功！"));
			}else{
				return renderResult(Global.TRUE, text("打款失败！：" + failIdAndInfo ));
			}

		}else{
			return renderResult(Global.TRUE, text("无效操作！"));
		}
	}

	// ------------------ 分销 -----------------------------
	/**
	 * 查询列表
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = {"listSell", ""})
	public String listSell(Sell sell, Model model) {
		model.addAttribute("sell", sell);
		return "modules/txsh/txshListSell";
	}

	/**
	 * 分销list界面
	 */
	@RequiresPermissions("txsh:txsh:view")
	@RequestMapping(value = "listDataSell")
	@ResponseBody
	public Page<Sell> listDataSell(Sell sell, HttpServletRequest request, HttpServletResponse response) {
		sell.setPage(new Page<>(request, response));
		Page<Sell> page = sellService.findPage(sell);
		return page;
	}

	/**
	 * 立即提现或者中止
	 * 状态1：结算中，
	 * 状态2：已结算，
	 * 状态3：批量审核不通过（收益扣除）,
	 * 状态4：已中止，
	 * 状态5：程序打款失败
	 * @return
	 */
	@RequestMapping(value = "updateSellPay")
	@ResponseBody
	public String updateSellPay(String str, String type) {
		//System.out.println("type: " + type + "\tstr:" + str);
		String[] strings = str.split(",");
		//中止
		if("4".equals(type)){
			for (String string : strings) {
				Sell sellTemp = new Sell(string);
				sellTemp.setZt(type);
				sellService.update(sellTemp);
			}
			return renderResult(Global.TRUE, text("批量中止操作成功！"));
		}else if("2".equals(type)){//打款
			boolean boo = true;
			String failId = "";
			for (String string : strings) {
				Sell sell = new Sell();
				sell.setId(string);
				Sell sellDb = sellService.get(sell);
				if("2".equals(sellDb.getZt()) || "3".equals(sellDb.getZt()) || "4".equals(sellDb.getZt())){//结算中1、程序打款失败5、的可再次打款
					boo = false;
					failId = string;
					break;
				}
				boolean booResult = sellService.sellTxsh(sellDb);
				if(!booResult){//如果有一个失败就中止打款
					boo = false;
					failId = string;
					break;
				}
			}
			if(boo){
				return renderResult(Global.TRUE, text("已调用商户付款成功！"));
			}else{
				return renderResult(Global.TRUE, text("打款失败！失败id：" + failId));
			}

		}else{
			return renderResult(Global.TRUE, text("无效操作！"));
		}
	}
}