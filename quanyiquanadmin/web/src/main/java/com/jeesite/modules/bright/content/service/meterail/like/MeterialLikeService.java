/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.service.meterail.like;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.content.entity.meterail.like.MeterialLike;
import com.jeesite.modules.bright.content.dao.meterail.like.MeterialLikeDao;

/**
 * 关注素材表Service
 * @author liqingfeng
 * @version 2019-07-18
 */
@Service
@Transactional(readOnly=true)
public class MeterialLikeService extends CrudService<MeterialLikeDao, MeterialLike> {

	public MeterialLike getMeterialLike(MeterialLike meterialLike) {
		return dao.getMeterialLike(meterialLike);
	}

	/**
	 * 获取单条数据
	 * @param meterialLike
	 * @return
	 */
	@Override
	public MeterialLike get(MeterialLike meterialLike) {
		return super.get(meterialLike);
	}
	
	/**
	 * 查询分页数据
	 * @param meterialLike 查询条件
	 * @param meterialLike.page 分页对象
	 * @return
	 */
	@Override
	public Page<MeterialLike> findPage(MeterialLike meterialLike) {
		return super.findPage(meterialLike);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param meterialLike
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MeterialLike meterialLike) {
		super.save(meterialLike);
	}
	
	/**
	 * 更新状态
	 * @param meterialLike
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MeterialLike meterialLike) {
		super.updateStatus(meterialLike);
	}
	
	/**
	 * 删除数据
	 * @param meterialLike
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MeterialLike meterialLike) {
		super.delete(meterialLike);
	}
	
}