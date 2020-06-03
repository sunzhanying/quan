/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.service.source;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.setfocus.entity.source.Source;
import com.jeesite.modules.bright.setfocus.dao.source.SourceDao;

/**
 * 来源表Service
 * @author liqingfeng
 * @version 2019-07-11
 */
@Service
@Transactional(readOnly=true)
public class SourceService extends CrudService<SourceDao, Source> {
	
	/**
	 * 获取单条数据
	 * @param source
	 * @return
	 */
	@Override
	public Source get(Source source) {
		return super.get(source);
	}
	
	/**
	 * 查询分页数据
	 * @param source 查询条件
	 * @param source.page 分页对象
	 * @return
	 */
	@Override
	public Page<Source> findPage(Source source) {
		return super.findPage(source);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param source
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Source source) {
		super.save(source);
	}
	
	/**
	 * 更新状态
	 * @param source
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Source source) {
		super.updateStatus(source);
	}
	
	/**
	 * 删除数据
	 * @param source
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Source source) {
		super.delete(source);
	}
	
}