/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.service.spb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.sp.entity.spb.Spbdy;
import com.jeesite.modules.bright.sp.dao.spb.SpbdyDao;
import com.jeesite.modules.bright.sp.entity.spb.SpbYhq;
import com.jeesite.modules.bright.sp.dao.spb.SpbYhqDao;
import com.jeesite.modules.bright.sp.entity.spb.Spbmx;
import com.jeesite.modules.bright.sp.dao.spb.SpbmxDao;

/**
 * 商品包定义Service
 * @author 马晓亮
 * @version 2019-07-01
 */
@Service
@Transactional(readOnly=true)
public class SpbdyService extends CrudService<SpbdyDao, Spbdy> {
	
	@Autowired
	private SpbYhqDao spbYhqDao;
	
	@Autowired
	private SpbmxDao spbmxDao;
	
	/**
	 * 获取单条数据
	 * @param spbdy
	 * @return
	 */
	@Override
	public Spbdy get(Spbdy spbdy) {
		Spbdy entity = super.get(spbdy);
		if (entity != null){
			SpbYhq spbYhq = new SpbYhq(entity);
			spbYhq.setStatus(SpbYhq.STATUS_NORMAL);
			entity.setSpbYhqList(spbYhqDao.findList(spbYhq));
			Spbmx spbmx = new Spbmx(entity);
			spbmx.setStatus(Spbmx.STATUS_NORMAL);
			entity.setSpbmxList(spbmxDao.findList(spbmx));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param spbdy 查询条件
	 * @param spbdy.page 分页对象
	 * @return
	 */
	@Override
	public Page<Spbdy> findPage(Spbdy spbdy) {
		Page<Spbdy>  spbdyPage = super.findPage(spbdy);
		for (Spbdy spbdy1:spbdyPage.getList()){
			//商品明细列表
			Spbmx spbmx = new Spbmx(spbdy1);
			spbmx.setStatus(Spbmx.STATUS_NORMAL);
			spbdy1.setSpbmxList(spbmxDao.findList(spbmx));
			//赠送优惠券列表
			SpbYhq spbYhq = new SpbYhq(spbdy1);
			spbYhq.setStatus(SpbYhq.STATUS_NORMAL);
			spbdy1.setSpbYhqList(spbYhqDao.findList(spbYhq));
		}
		return spbdyPage;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param spbdy
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Spbdy spbdy) {
		super.save(spbdy);
		// 保存 Spbdy子表
		for (SpbYhq spbYhq : spbdy.getSpbYhqList()){
			if (!SpbYhq.STATUS_DELETE.equals(spbYhq.getStatus())){
				spbYhq.setSpbid(spbdy);
				if (spbYhq.getIsNewRecord()){
					spbYhqDao.insert(spbYhq);
				}else{
					spbYhqDao.update(spbYhq);
				}
			}else{
				spbYhqDao.delete(spbYhq);
			}
		}
		// 保存 Spbdy子表
		for (Spbmx spbmx : spbdy.getSpbmxList()){
			if (!Spbmx.STATUS_DELETE.equals(spbmx.getStatus())){
				spbmx.setSpbid(spbdy);
				if (spbmx.getIsNewRecord()){
					spbmxDao.insert(spbmx);
				}else{
					spbmxDao.update(spbmx);
				}
			}else{
				spbmxDao.delete(spbmx);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param spbdy
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Spbdy spbdy) {
		super.updateStatus(spbdy);
	}
	
	/**
	 * 删除数据
	 * @param spbdy
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Spbdy spbdy) {
		super.delete(spbdy);
		SpbYhq spbYhq = new SpbYhq();
		spbYhq.setSpbid(spbdy);
		spbYhqDao.deleteByEntity(spbYhq);
		Spbmx spbmx = new Spbmx();
		spbmx.setSpbid(spbdy);
		spbmxDao.deleteByEntity(spbmx);
	}
	
}