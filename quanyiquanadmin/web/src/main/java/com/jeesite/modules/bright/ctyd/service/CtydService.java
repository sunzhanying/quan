/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.ctyd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.ctyd.entity.Ctyd;
import com.jeesite.modules.bright.ctyd.dao.CtydDao;

/**
 * 餐厅预订表Service
 * @author 马晓亮
 * @version 2019-07-24
 */
@Service
@Transactional(readOnly=true)
public class CtydService extends CrudService<CtydDao, Ctyd> {

	/***
	 * 更新订单状态
	 * @param id
	 * @param zt
	 * @return
	 */
	@Transactional(readOnly=false)
	public int updateZt(String id,int zt){
		return dao.updateZt(id, zt);
	}
	
	/**
	 * 获取单条数据
	 * @param ctyd
	 * @return
	 */
	@Override
	public Ctyd get(Ctyd ctyd) {
		return super.get(ctyd);
	}
	
	/**
	 * 查询分页数据
	 * @param ctyd 查询条件
	 * @return
	 */
	@Override
	public Page<Ctyd> findPage(Ctyd ctyd) {
		Page<Ctyd> page = new Page<>();
		ctyd.setPage(page);
		page.setList(dao.queryCtydList(ctyd));
		return page;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param ctyd
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Ctyd ctyd) {
		super.save(ctyd);
	}
	
	/**
	 * 更新状态
	 * @param ctyd
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Ctyd ctyd) {
		super.updateStatus(ctyd);
	}
	
	/**
	 * 删除数据
	 * @param ctyd
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Ctyd ctyd) {
		super.delete(ctyd);
	}
	
}