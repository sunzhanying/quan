/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.service.push;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.push.entity.push.Push;
import com.jeesite.modules.bright.push.dao.push.PushDao;

/**
 * t_pushService
 * @author 李金辉
 * @version 2019-07-18
 */
@Service
@Transactional(readOnly=true)
public class PushService extends CrudService<PushDao, Push> {
	
	/**
	 * 获取单条数据
	 * @param push
	 * @return
	 */
	@Override
	public Push get(Push push) {
		return super.get(push);
	}
	
	/**
	 * 查询分页数据
	 * @param push 查询条件
	 * @param push.page 分页对象
	 * @return
	 */
	@Override
	public Page<Push> findPage(Push push) {
		return super.findPage(push);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param push
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Push push) {
		super.save(push);
	}
	
	/**
	 * 更新状态
	 * @param push
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Push push) {
		super.updateStatus(push);
	}
	
	/**
	 * 删除数据
	 * @param push
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Push push) {
		super.delete(push);
	}

	@Transactional(readOnly = false)
	public List<Push> push(Push push){
		return dao.push(push);
	}

	public int queryQdTsjls(String dictLabel) {
		return dao.queryQdTsjls(dictLabel);
	}

	public int ytsl() {
		return dao.ytsl();
	}

	public int success() {
		return dao.success();
	}

	public int error() {
		return dao.error();
	}

	public int dtsl() {
		return dao.dtsl();
	}
}