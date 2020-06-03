/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.hyk.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.hyk.entity.VipCard;
import com.jeesite.modules.bright.hyk.dao.VipCardDao;

/**
 * 会员卡Service
 * @author 马晓亮
 * @version 2019-08-15
 */
@Service
@Transactional(readOnly=true)
public class VipCardService extends CrudService<VipCardDao, VipCard> {

	/**
	 * 更新上下架
	 * @param sxj
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=false)
	public int updateSxj(String sxj, String id){
		return dao.updateSxj(sxj, id);
	}

	/**
	 * 获取单条数据
	 * @param vipCard
	 * @return
	 */
	@Override
	public VipCard get(VipCard vipCard) {
		return super.get(vipCard);
	}
	
	/**
	 * 查询分页数据
	 * @param vipCard 查询条件
	 * @param vipCard.page 分页对象
	 * @return
	 */
	@Override
	public Page<VipCard> findPage(VipCard vipCard) {
		return super.findPage(vipCard);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param vipCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(VipCard vipCard) {
		super.save(vipCard);
	}
	
	/**
	 * 更新状态
	 * @param vipCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(VipCard vipCard) {
		super.updateStatus(vipCard);
	}
	
	/**
	 * 删除数据
	 * @param vipCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(VipCard vipCard) {
		super.delete(vipCard);
	}
	
}