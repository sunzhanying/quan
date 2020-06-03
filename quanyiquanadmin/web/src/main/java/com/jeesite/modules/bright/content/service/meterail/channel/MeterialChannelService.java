/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.service.meterail.channel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.content.entity.meterail.channel.MeterialChannel;
import com.jeesite.modules.bright.content.dao.meterail.channel.MeterialChannelDao;

/**
 * 渠道表Service
 * @author liqingfeng
 * @version 2019-07-09
 */
@Service
@Transactional(readOnly=true)
public class MeterialChannelService extends CrudService<MeterialChannelDao, MeterialChannel> {
	
	/**
	 * 获取单条数据
	 * @param meterialChannel
	 * @return
	 */
	@Override
	public MeterialChannel get(MeterialChannel meterialChannel) {
		return super.get(meterialChannel);
	}
	
	/**
	 * 查询分页数据
	 * @param meterialChannel 查询条件
	 * @param meterialChannel.page 分页对象
	 * @return
	 */
	@Override
	public Page<MeterialChannel> findPage(MeterialChannel meterialChannel) {
		return super.findPage(meterialChannel);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param meterialChannel
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MeterialChannel meterialChannel) {
		super.save(meterialChannel);
	}
	
	/**
	 * 更新状态
	 * @param meterialChannel
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MeterialChannel meterialChannel) {
		super.updateStatus(meterialChannel);
	}
	
	/**
	 * 删除数据
	 * @param meterialChannel
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MeterialChannel meterialChannel) {
		super.delete(meterialChannel);
	}
	
}