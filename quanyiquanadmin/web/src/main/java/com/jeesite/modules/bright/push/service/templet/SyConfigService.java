/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.service.templet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.push.entity.templet.SyConfig;
import com.jeesite.modules.bright.push.dao.templet.SyConfigDao;

/**
 * 生涯配置表，将模板配置给客户（学生）、群组。（配置二选一，即一般配置给群组，亦可配置给客户）流程：1、配置模板2、配置模板适用对象：群组、客户、学生3、生涯任务生成：读此表，结合生涯模板表。生成t_sy_rwmx，同是根据推送渠道生成等推送记录写入push表界面：生涯生成界面要定制，Service
 * @author 李金辉
 * @version 2019-07-17
 */
@Service
@Transactional(readOnly=true)
public class SyConfigService extends CrudService<SyConfigDao, SyConfig> {
	
	/**
	 * 获取单条数据
	 * @param syConfig
	 * @return
	 */
	@Override
	public SyConfig get(SyConfig syConfig) {
		return super.get(syConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param syConfig 查询条件
	 * @param syConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<SyConfig> findPage(SyConfig syConfig) {
		return super.findPage(syConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param syConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SyConfig syConfig) {
		super.save(syConfig);
	}
	
	/**
	 * 更新状态
	 * @param syConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SyConfig syConfig) {
		super.updateStatus(syConfig);
	}
	
	/**
	 * 删除数据
	 * @param syConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SyConfig syConfig) {
		super.delete(syConfig);
	}


	@Transactional(readOnly = false)
	public SyConfig push(SyConfig syConfig){
		return dao.push(syConfig);
	}

}