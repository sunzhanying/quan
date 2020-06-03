/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.collect.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.collect.entity.Collect;
import com.jeesite.modules.collect.dao.CollectDao;

/**
 * 收藏权益Service
 * @author 收藏
 * @version 2020-03-30
 */
@Service
@Transactional(readOnly=true)
public class CollectService extends CrudService<CollectDao, Collect> {
	
	/**
	 * 获取单条数据
	 * @param collect
	 * @return
	 */
	@Override
	public Collect get(Collect collect) {
		return super.get(collect);
	}
	
	/**
	 * 查询分页数据
	 * @param collect 查询条件
	 * @param collect.page 分页对象
	 * @return
	 */
	@Override
	public Page<Collect> findPage(Collect collect) {
		return super.findPage(collect);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param collect
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Collect collect) {
		super.save(collect);
	}
	
	/**
	 * 更新状态
	 * @param collect
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Collect collect) {
		super.updateStatus(collect);
	}
	
	/**
	 * 删除数据
	 * @param collect
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Collect collect) {
		super.delete(collect);
	}
	
}