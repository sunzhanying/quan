/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.service.xw;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.t.entity.xw.Xw;
import com.jeesite.modules.bright.t.dao.xw.XwDao;

/**
 * 客户行为配置Service
 * @author 李金辉
 * @version 2019-06-27
 */
@Service
@Transactional(readOnly=true)
public class XwService extends CrudService<XwDao, Xw> {
	
	/**
	 * 获取单条数据
	 * @param xw
	 * @return
	 */
	@Override
	public Xw get(Xw xw) {
		return super.get(xw);
	}
	
	/**
	 * 查询分页数据
	 * @param xw 查询条件
	 * @param xw.page 分页对象
	 * @return
	 */
	@Override
	public Page<Xw> findPage(Xw xw) {
		return super.findPage(xw);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param xw
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Xw xw) {
		super.save(xw);
	}
	
	/**
	 * 更新状态
	 * @param xw
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Xw xw) {
		super.updateStatus(xw);
	}
	
	/**
	 * 删除数据
	 * @param xw
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Xw xw) {
		super.delete(xw);
	}
	
}