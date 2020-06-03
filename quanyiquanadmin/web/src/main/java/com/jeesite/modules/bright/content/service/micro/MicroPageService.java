/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.service.micro;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.content.entity.micro.MicroPage;
import com.jeesite.modules.bright.content.dao.micro.MicroPageDao;

/**
 * 微页面Service
 * @author liqingfeng
 * @version 2019-06-18
 */
@Service
@Transactional(readOnly=true)
public class MicroPageService extends CrudService<MicroPageDao, MicroPage> {
	
	/**
	 * 获取单条数据
	 * @param microPage
	 * @return
	 */
	@Override
	public MicroPage get(MicroPage microPage) {
		return super.get(microPage);
	}
	
	/**
	 * 查询分页数据
	 * @param microPage 查询条件
	 * @param microPage.page 分页对象
	 * @return
	 */
	@Override
	public Page<MicroPage> findPage(MicroPage microPage) {
		return super.findPage(microPage);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param microPage
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MicroPage microPage) {
		super.save(microPage);
	}
	
	/**
	 * 更新状态
	 * @param microPage
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MicroPage microPage) {
		super.updateStatus(microPage);
	}
	
	/**
	 * 删除数据
	 * @param microPage
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MicroPage microPage) {
		super.delete(microPage);
	}
	
}