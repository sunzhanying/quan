/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.service;

import com.jeesite.API.weixin.api.PayMchAPI;
import com.jeesite.API.weixin.bean.paymch.Transfers;
import com.jeesite.API.weixin.bean.paymch.TransfersResult;
import com.jeesite.API.weixin.util.IdGen;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.txsh.dao.SellDao;
import com.jeesite.modules.txsh.entity.Sell;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 分账提现审核Service
 */
@Service
@Transactional(readOnly=true)
public class SellService extends CrudService<SellDao, Sell> {

	private final Log log = LogFactory.getLog(getClass());

	@Value("${weixin.appidA}")
	private String wxAppId;
	@Value("${weixin.Mch_id}")
	private String Mch_id;
	@Value("${weixin.Mch_key}")
	private String key;

	/*@Value("${weixin.Mch_pay_id}")
	private String Mch_pay_id;
	@Value("${weixin.Mch_pay_key}")
	private String payKey;*/

	@Autowired
	private KhXxDao khXxDao;

	/**
	 * 提现审核
	 */
	@Transactional(readOnly=false)
	public boolean sellTxsh(Sell sell){
		boolean boo = false;
		KhXx khXx = khXxDao.get(new KhXx(sell.getKhid()));
		//企业付款
		Transfers transfers = new Transfers();
		transfers.setMch_appid(wxAppId);
		transfers.setMchid(Mch_id);
		transfers.setNonce_str(IdGen.wxRandom(32));
		transfers.setPartner_trade_no(sell.getId());
		transfers.setOpenid(khXx.getOpenId());
		transfers.setCheck_name("NO_CHECK");
		transfers.setAmount(String.valueOf((int)(sell.getTxje() * 100)));//int 单位为分，需要乘以100
		transfers.setDesc("提现申请");
		log.info(transfers);
		TransfersResult transfersResult = null;
		try {
			transfersResult = PayMchAPI.mmpaymkttransfersPromotionTransfers(transfers, key);
		}catch (Exception e){
			e.printStackTrace();
			//return new Response(10000, "向用户付款出现错误", null);
		}

		log.info(transfersResult);
		log.info("分销打款、企业付款返回信息：" + transfersResult.getReturn_msg());
		//成功返回
		if ("SUCCESS".equals(transfersResult.getResult_code()) && "SUCCESS".equals(transfersResult.getReturn_code())) {
			//更新申请单状态
			sell.setZt(Sell.SELL_STATUS_TG);
			dao.update(sell);
			boo = true;
			//return new Response(Code.SUCCESS);
		} else {
			///return new Response(10000,transfersResult.getReturn_msg(), null);
			log.info("付款状态返回： " + transfersResult.getReturn_msg());
			//更新申请单状态为：程序提现失败
			sell.setZt(Sell.SELL_STATUS_FAIL);
			dao.update(sell);
		}
		return boo;
	}


	/**
	 * 获取单条数据
	 * @return
	 */
	@Override
	public Sell get(Sell sell) {
		return super.get(sell);
	}
	
	/**
	 * 查询分页数据
	 * @return
	 */
	@Override
	public Page<Sell> findPage(Sell sell) {
		return super.findPage(sell);
	}


	/**
	 * 保存数据（插入或更新）
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Sell sell) {
		super.save(sell);
	}
	
	/**
	 * 更新状态
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Sell sell) {
		super.updateStatus(sell);
	}
	
	/**
	 * 删除数据
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Sell sell) {
		super.delete(sell);
	}

	/**
	 * 更新状态
	 */
	@Override
	@Transactional(readOnly=false)
	public void update(Sell sell) {
		super.update(sell);
	}

	/**
	 * 查询分页数据
	 * @param
	 * @return
	 */
	public List<Map<String,String>> findPayPage(Map<String,String> param) {
		String orderId = param.get("orderId");
		String id = param.get("id");
		String wxnc = param.get("wxnc");
		String zt = param.get("zt");
		String startDate = param.get("startDate");
		String endDate = param.get("endDate");
		String type = param.get("type");
		List<Map<String,String>> list = dao.findAllList(orderId,id,wxnc,zt,startDate,endDate,type);
		return list;
	}

	/**
	 * 查询已到账收益
	 * @param
	 * @return
	 */
	public Double findMyMoney(String khid,String zt,String todayStr) {
		Double ydz = dao.findMyMoney(khid,zt,todayStr);
		return ydz;
	}
	
}