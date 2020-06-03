/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.khcc.entity.KhCc;
import com.jeesite.modules.bright.khcc.dao.KhCcDao;

/**
 * 客户持仓Service
 * @author 马晓亮
 * @version 2019-07-16
 */
@Service
@Transactional(readOnly=true)
public class KhCcService extends CrudService<KhCcDao, KhCc> {
	
	/**
	 * 获取单条数据
	 * @param khCc
	 * @return
	 */
	@Override
	public KhCc get(KhCc khCc) {
		return super.get(khCc);
	}
	
	/**
	 * 查询分页数据
	 * @param khCc 查询条件
	 * @param khCc.page 分页对象
	 * @return
	 */
	@Override
	public Page<KhCc> findPage(KhCc khCc) {
		return super.findPage(khCc);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khCc
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhCc khCc) {
		super.save(khCc);
	}
	
	/**
	 * 更新状态
	 * @param khCc
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhCc khCc) {
		super.updateStatus(khCc);
	}
	
	/**
	 * 删除数据
	 * @param khCc
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhCc khCc) {
		super.delete(khCc);
	}
	
}