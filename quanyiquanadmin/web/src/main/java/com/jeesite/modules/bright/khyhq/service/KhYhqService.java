/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khyhq.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.bright.sp.entity.yhq.SpYhq;
import com.jeesite.modules.bright.sp.service.yhq.SpYhqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.khyhq.entity.KhYhq;
import com.jeesite.modules.bright.khyhq.dao.KhYhqDao;

/**
 * 客户优惠券Service
 * @author 马晓亮
 * @version 2019-07-25
 */
@Service
@Transactional(readOnly=true)
public class KhYhqService extends CrudService<KhYhqDao, KhYhq> {

	@Autowired
	private SpYhqService spYhqService;
	@Autowired
	private KhYhqDao khYhqDao;

	public List<KhYhq> findAllList(String userid, int zt, String je) {
		return dao.findAllList(userid, zt, je);
	}

	/**
	 * 保存数据优惠券
	 */
	@Transactional(readOnly=false)
	public Response saveKhYhq(String yhqid, String khid) {
		//判断是否已领取优惠券
		if (khYhqDao.countYhqByYhqId(khid, yhqid) > 0){
			return new Response(Code.API_YHQ_YLQ);
		}
		SpYhq spYhq = spYhqService.get(yhqid);
		if (spYhq != null){
			KhYhq khYhq = new KhYhq();
			khYhq.setKhid(khid);
			khYhq.setZt(KhYhq.KHYHQ_ZT_WSY);
			khYhq.setYhqid(yhqid);
			khYhq.setType(spYhq.getType());
			khYhq.setYhName(spYhq.getYhName());
			khYhq.setYhFname(spYhq.getYhFname());
			khYhq.setYhImg(spYhq.getYhImg());
			khYhq.setYhJe(spYhq.getYhJe());
			khYhq.setYhMj(spYhq.getYhMj());
			//固定日期
			if (SpYhq.YHQ_SXLX_GD.equals(spYhq.getYhSxlx())){
				khYhq.setStartDate(spYhq.getYhStart());
				khYhq.setEndDate(spYhq.getYhEnd());
			}
			//日
			if (SpYhq.YHQ_SXLX_RSX.equals(spYhq.getYhSxlx())){
				khYhq.setStartDate(new Date());
				khYhq.setEndDate(DateUtils.parseDate(DateUtils.getDate("yyyy-MM-dd", Integer.parseInt(spYhq.getYhRsx()), Calendar.DATE)));
			}
			super.save(khYhq);
		}
		return new Response(Code.SUCCESS);
	}
	
	/**
	 * 获取单条数据
	 * @param khYhq
	 * @return
	 */
	@Override
	public KhYhq get(KhYhq khYhq) {
		return super.get(khYhq);
	}
	
	/**
	 * 查询分页数据
	 * @param khYhq 查询条件
	 * @param khYhq.page 分页对象
	 * @return
	 */
	@Override
	public Page<KhYhq> findPage(KhYhq khYhq) {
		return super.findPage(khYhq);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khYhq
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhYhq khYhq) {
		super.save(khYhq);
	}
	
	/**
	 * 更新状态
	 * @param khYhq
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhYhq khYhq) {
		super.updateStatus(khYhq);
	}
	
	/**
	 * 删除数据
	 * @param khYhq
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhYhq khYhq) {
		super.delete(khYhq);
	}
	
}