/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.service;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.weixin.api.PayMchAPI;
import com.jeesite.API.weixin.bean.paymch.Transfers;
import com.jeesite.API.weixin.bean.paymch.TransfersResult;
import com.jeesite.API.weixin.util.IdGen;
import com.jeesite.API.weixin.util.PayUtil;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.txsh.entity.Txsh;
import com.jeesite.modules.txsh.dao.TxshDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提现审核Service
 * @author 马晓亮
 * @version 2020-03-31
 */
@Service
@Transactional(readOnly=true)
public class TxshService extends CrudService<TxshDao, Txsh> {

	private final Log log = LogFactory.getLog(getClass());

	@Value("${weixin.appid}")
	private String wxAppId;
	@Value("${weixin.Mch_id}")
	private String Mch_id;
	@Value("${weixin.Mch_key}")
	private String key;

	@Autowired
	private QyhsMxDao qyhsMxDao;
	@Autowired
	private KhXxDao khXxDao;

	/**
	 * 提现审核
	 * @param txsh
	 */
	@Transactional(readOnly=false)
	public void txsh(Txsh txsh){
		KhXx khXx = khXxDao.get(new KhXx(txsh.getKhid()));
		//企业付款
		Transfers transfers = new Transfers();
		transfers.setMch_appid(wxAppId);
		transfers.setMchid(Mch_id);
		transfers.setNonce_str(IdGen.wxRandom(32));
		transfers.setPartner_trade_no(txsh.getId());
		transfers.setOpenid(khXx.getOpenId());
		transfers.setCheck_name("NO_CHECK");
		transfers.setAmount(String.valueOf((int)(txsh.getTxje() * 100)));//int 单位为分，需要乘以100
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
		log.info("企业付款返回信息：" + transfersResult.getReturn_msg());
		//成功返回
		if ("SUCCESS".equals(transfersResult.getResult_code()) && "SUCCESS".equals(transfersResult.getReturn_code())) {
			//更新申请单状态
			txsh.setZt(Txsh.TX_STATUS_TG);
			dao.update(txsh);
			//更新权益明细结算状态
			//向权益明细中修改申请单号
			QyhsMx qyhsMx = new QyhsMx();
			qyhsMx.setJszt(QyhsMx.STATUS_JS_YJS);
			//条件
			QyhsMx qyhsMx1 = new QyhsMx();
			qyhsMx1.setSqdh(txsh.getId());
			qyhsMxDao.updateByEntity(qyhsMx, qyhsMx1);
			//return new Response(Code.SUCCESS);
		} else {
			///return new Response(10000,transfersResult.getReturn_msg(), null);
			log.info("付款状态返回： " + transfersResult.getReturn_msg());
			//更新申请单状态为：程序提现失败
			txsh.setZt(Txsh.TX_STATUS_FAIL);
			dao.update(txsh);
		}
	}

	//生成提现单
	@Transactional(readOnly=false)
	public void saveTxd(Txsh txsh) {
		txsh.preInsert();
		dao.insert(txsh);
		//向权益明细中修改申请单号
		QyhsMx qyhsMx = new QyhsMx();
		qyhsMx.setJszt(QyhsMx.STATUS_JS_JSZ);
		qyhsMx.setSqdh(txsh.getId());
		//条件
		QyhsMx qyhsMx1 = new QyhsMx();
		qyhsMx1.setKhid(txsh.getKhid());
		qyhsMx1.setOrderId(txsh.getOrderId());
		//qyhsMx1.setJszt(QyhsMx.STATUS_JS_WJS);
		qyhsMxDao.updateByEntity(qyhsMx, qyhsMx1);
	}
	
	/**
	 * 获取单条数据
	 * @param txsh
	 * @return
	 */
	@Override
	public Txsh get(Txsh txsh) {
		return super.get(txsh);
	}
	
	/**
	 * 查询分页数据
	 * @param txsh 查询条件
	 * @return
	 */
	@Override
	public Page<Txsh> findPage(Txsh txsh) {
		return super.findPage(txsh);
	}

	/**
	 * 查询分页数据
	 * @param
	 * @return
	 */
	public List<Map<String,String>> findPayPage(Map<String,String> param) {
		String orderId = param.get("orderId");
		List<Map<String,String>> list = dao.findAllList(orderId);
		return list;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param txsh
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Txsh txsh) {
		super.save(txsh);
	}
	
	/**
	 * 更新状态
	 * @param txsh
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Txsh txsh) {
		super.updateStatus(txsh);
	}
	
	/**
	 * 删除数据
	 * @param txsh
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Txsh txsh) {
		super.delete(txsh);
	}

	/**
	 * 更新状态
	 * @param txsh
	 */
	@Override
	@Transactional(readOnly=false)
	public void update(Txsh txsh) {
		super.update(txsh);
	}
	
}