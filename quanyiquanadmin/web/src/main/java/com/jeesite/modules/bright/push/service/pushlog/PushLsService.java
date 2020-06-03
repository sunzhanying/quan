/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.service.pushlog;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.push.entity.pushlog.PushLs;
import com.jeesite.modules.bright.push.dao.pushlog.PushLsDao;

/**
 * 推送批次记录流水表用于记录每次生成的推送批次Service
 * @author 李金辉
 * @version 2019-07-18
 */
@Service
@Transactional(readOnly=true)
public class PushLsService extends CrudService<PushLsDao, PushLs> {
	
	/**
	 * 获取单条数据
	 * @param pushLs
	 * @return
	 */
	@Override
	public PushLs get(PushLs pushLs) {
		return super.get(pushLs);
	}
	
	/**
	 * 查询分页数据
	 * @param pushLs 查询条件
	 * @param pushLs.page 分页对象
	 * @return
	 */
	@Override
	public Page<PushLs> findPage(PushLs pushLs) {
		return super.findPage(pushLs);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param pushLs
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PushLs pushLs) {
		super.save(pushLs);
	}
	
	/**
	 * 更新状态
	 * @param pushLs
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PushLs pushLs) {
		super.updateStatus(pushLs);
	}
	
	/**
	 * 删除数据
	 * @param pushLs
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PushLs pushLs) {
		super.delete(pushLs);
	}
	
}