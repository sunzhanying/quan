/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyjg.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.qyjg.entity.Qyjg;
import com.jeesite.modules.qyjg.dao.QyjgDao;

/**
 * 权益券价格Service
 * @author 马晓亮
 * @version 2020-03-24
 */
@Service
@Transactional(readOnly=true)
public class QyjgService extends CrudService<QyjgDao, Qyjg> {
	
	/**
	 * 获取单条数据
	 * @param qyjg
	 * @return
	 */
	@Override
	public Qyjg get(Qyjg qyjg) {
		return super.get(qyjg);
	}
	
	/**
	 * 查询分页数据
	 * @param qyjg 查询条件
	 * @param qyjg.page 分页对象
	 * @return
	 */
	@Override
	public Page<Qyjg> findPage(Qyjg qyjg) {
		return super.findPage(qyjg);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param qyjg
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Qyjg qyjg) {
		super.save(qyjg);
	}
	
	/**
	 * 更新状态
	 * @param qyjg
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Qyjg qyjg) {
		super.updateStatus(qyjg);
	}
	
	/**
	 * 删除数据
	 * @param qyjg
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Qyjg qyjg) {
		super.delete(qyjg);
	}
	
}