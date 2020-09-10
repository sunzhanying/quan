/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.service.sptype;


import com.jeesite.modules.bright.sp.dao.SpXxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.sp.entity.sptype.SpType;
import com.jeesite.modules.bright.sp.dao.sptype.SpTypeDao;

import java.util.List;
import java.util.Map;

/**
 * 商品类型Service
 * @author 马晓亮
 * @version 2019-06-25
 */
@Service
@Transactional(readOnly=true)
public class SpTypeService extends CrudService<SpTypeDao, SpType> {

	@Autowired
	private SpXxDao spXxDao;

	@Autowired
	private SpTypeDao spTypeDao;
	
	/**
	 * 获取单条数据
	 * @param spType
	 * @return
	 */
	@Override
	public SpType get(SpType spType) {
		return super.get(spType);
	}
	
	/**
	 * 查询分页数据
	 * @param spType 查询条件
	 * @return
	 */
	@Override
	public Page<SpType> findPage(SpType spType) {
		return super.findPage(spType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param spType
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SpType spType) {
		super.save(spType);
	}
	
	/**
	 * 更新状态
	 * @param spType
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SpType spType) {
		super.updateStatus(spType);
	}
	
	/**
	 * 删除数据
	 * @param spType
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SpType spType) {
		spXxDao.updateBySpTypeId(spType.getId());
		super.delete(spType);
	}

	public List<Map<String,String>> findTwoSpList(String parent) {
		return spTypeDao.findTwoSpList(parent);
	}

	public List<String> findTwoIds(String parent) {
		return spTypeDao.findTwoIds(parent);
	}
}