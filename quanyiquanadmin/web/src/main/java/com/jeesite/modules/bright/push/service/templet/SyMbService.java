/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.service.templet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.push.entity.templet.SyMb;
import com.jeesite.modules.bright.push.dao.templet.SyMbDao;
import com.jeesite.modules.bright.push.entity.templet.SyMbmx;
import com.jeesite.modules.bright.push.dao.templet.SyMbmxDao;

/**
 * 生涯模板Service
 * @author 李金辉
 * @version 2019-07-17
 */
@Service
@Transactional(readOnly=true)
public class SyMbService extends CrudService<SyMbDao, SyMb> {
	
	@Autowired
	private SyMbmxDao syMbmxDao;
	
	/**
	 * 获取单条数据
	 * @param syMb
	 * @return
	 */
	@Override
	public SyMb get(SyMb syMb) {
		SyMb entity = super.get(syMb);
		if (entity != null){
			SyMbmx syMbmx = new SyMbmx(entity);
			syMbmx.setStatus(SyMbmx.STATUS_NORMAL);
			entity.setSyMbmxList(syMbmxDao.findList(syMbmx));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param syMb 查询条件
	 * @param syMb.page 分页对象
	 * @return
	 */
	@Override
	public Page<SyMb> findPage(SyMb syMb) {
		Page<SyMb> page=super.findPage(syMb);
		for (SyMb entity: page.getList()) {
			if (entity != null){
				SyMbmx syMbmx = new SyMbmx(entity);
				syMbmx.setStatus(SyMbmx.STATUS_NORMAL);
				entity.setSyMbmxList(syMbmxDao.findList(syMbmx));
			}
		}
		return page;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param syMb
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SyMb syMb) {
		super.save(syMb);
		// 保存 SyMb子表
		for (SyMbmx syMbmx : syMb.getSyMbmxList()){
			if (!SyMbmx.STATUS_DELETE.equals(syMbmx.getStatus())){
				syMbmx.setMbid(syMb);
				if (syMbmx.getIsNewRecord()){
					syMbmxDao.insert(syMbmx);
				}else{
					syMbmxDao.update(syMbmx);
				}
			}else{
				syMbmxDao.delete(syMbmx);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param syMb
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SyMb syMb) {
		super.updateStatus(syMb);
	}
	
	/**
	 * 删除数据
	 * @param syMb
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SyMb syMb) {
		super.delete(syMb);
		SyMbmx syMbmx = new SyMbmx();
		syMbmx.setMbid(syMb);
		syMbmxDao.deleteByEntity(syMbmx);
	}
	
}