/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.wx.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.wx.dao.WxConfigDao;
import com.jeesite.modules.bright.wx.entity.WxConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 微信配置Service
 * @author 李金辉
 * @version 2019-07-26
 */
@Service
@Transactional(readOnly=true)
public class WxConfigService extends CrudService<WxConfigDao, WxConfig> {
	
	/**
	 * 获取单条数据
	 * @param wxConfig
	 * @return
	 */
	@Override
	public WxConfig get(WxConfig wxConfig) {
		return super.get(wxConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param wxConfig 查询条件
	 * @param wxConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<WxConfig> findPage(WxConfig wxConfig) {
		return super.findPage(wxConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param wxConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(WxConfig wxConfig) {
		super.save(wxConfig);
	}
	
	/**
	 * 更新状态
	 * @param wxConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(WxConfig wxConfig) {
		super.updateStatus(wxConfig);
	}
	
	/**
	 * 删除数据
	 * @param wxConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(WxConfig wxConfig) {
		super.delete(wxConfig);
	}
	
}