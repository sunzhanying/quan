/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.service.others.externallink;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.setfocus.entity.others.externallink.MeterialExternalLink;
import com.jeesite.modules.bright.setfocus.dao.others.externallink.MeterialExternalLinkDao;

/**
 * 外部链接配置Service
 * @author liqingfeng
 * @version 2019-07-12
 */
@Service
@Transactional(readOnly=true)
public class MeterialExternalLinkService extends CrudService<MeterialExternalLinkDao, MeterialExternalLink> {
	
	/**
	 * 获取单条数据
	 * @param meterialExternalLink
	 * @return
	 */
	@Override
	public MeterialExternalLink get(MeterialExternalLink meterialExternalLink) {
		return super.get(meterialExternalLink);
	}
	
	/**
	 * 查询分页数据
	 * @param meterialExternalLink 查询条件
	 * @param meterialExternalLink.page 分页对象
	 * @return
	 */
	@Override
	public Page<MeterialExternalLink> findPage(MeterialExternalLink meterialExternalLink) {
		return super.findPage(meterialExternalLink);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param meterialExternalLink
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MeterialExternalLink meterialExternalLink) {
		super.save(meterialExternalLink);
	}
	
	/**
	 * 更新状态
	 * @param meterialExternalLink
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MeterialExternalLink meterialExternalLink) {
		super.updateStatus(meterialExternalLink);
	}
	
	/**
	 * 删除数据
	 * @param meterialExternalLink
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MeterialExternalLink meterialExternalLink) {
		super.delete(meterialExternalLink);
	}
	
}