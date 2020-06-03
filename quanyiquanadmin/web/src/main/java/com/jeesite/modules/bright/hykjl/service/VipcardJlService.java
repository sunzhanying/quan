/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.hykjl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.hykjl.entity.VipcardJl;
import com.jeesite.modules.bright.hykjl.dao.VipcardJlDao;

/**
 * 会员卡消费记录Service
 * @author 马晓亮
 * @version 2019-08-20
 */
@Service
@Transactional(readOnly=true)
public class VipcardJlService extends CrudService<VipcardJlDao, VipcardJl> {
	
	/**
	 * 获取单条数据
	 * @param vipcardJl
	 * @return
	 */
	@Override
	public VipcardJl get(VipcardJl vipcardJl) {
		return super.get(vipcardJl);
	}
	
	/**
	 * 查询分页数据
	 * @param vipcardJl 查询条件
	 * @param vipcardJl.page 分页对象
	 * @return
	 */
	@Override
	public Page<VipcardJl> findPage(VipcardJl vipcardJl) {
		return super.findPage(vipcardJl);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param vipcardJl
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(VipcardJl vipcardJl) {
		super.save(vipcardJl);
	}
	
	/**
	 * 更新状态
	 * @param vipcardJl
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(VipcardJl vipcardJl) {
		super.updateStatus(vipcardJl);
	}
	
	/**
	 * 删除数据
	 * @param vipcardJl
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(VipcardJl vipcardJl) {
		super.delete(vipcardJl);
	}
	
}