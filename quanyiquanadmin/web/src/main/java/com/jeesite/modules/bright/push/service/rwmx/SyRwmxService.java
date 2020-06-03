/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.service.rwmx;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.push.entity.rwmx.SyRwmx;
import com.jeesite.modules.bright.push.dao.rwmx.SyRwmxDao;

/**
 * 生涯任务明细Service
 * @author 李金辉
 * @version 2019-07-18
 */
@Service
@Transactional(readOnly=true)
public class SyRwmxService extends CrudService<SyRwmxDao, SyRwmx> {
	
	/**
	 * 获取单条数据
	 * @param syRwmx
	 * @return
	 */
	@Override
	public SyRwmx get(SyRwmx syRwmx) {
		return super.get(syRwmx);
	}
	
	/**
	 * 查询分页数据
	 * @param syRwmx 查询条件
	 * @param syRwmx.page 分页对象
	 * @return
	 */
	@Override
	public Page<SyRwmx> findPage(SyRwmx syRwmx) {
		return super.findPage(syRwmx);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param syRwmx
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SyRwmx syRwmx) {
		super.save(syRwmx);
	}
	
	/**
	 * 更新状态
	 * @param syRwmx
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SyRwmx syRwmx) {
		super.updateStatus(syRwmx);
	}
	
	/**
	 * 删除数据
	 * @param syRwmx
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SyRwmx syRwmx) {
		super.delete(syRwmx);
	}
	
}