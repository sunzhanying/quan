/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.service.propagate;

import com.jeesite.API.service.Response;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.t.entity.propagate.KhPropagate;
import com.jeesite.modules.bright.t.dao.propagate.KhPropagateDao;

import java.util.List;

/**
 * 客户传播Service
 * @author 李金辉
 * @version 2019-07-08
 */
@Service
@Transactional(readOnly=true)
public class KhPropagateService extends CrudService<KhPropagateDao, KhPropagate> {
	
	/**
	 * 获取单条数据
	 * @param khPropagate
	 * @return
	 */
	@Override
	public KhPropagate get(KhPropagate khPropagate) {
		return super.get(khPropagate);
	}
	
	/**
	 * 查询分页数据
	 * @param khPropagate 查询条件
	 * @param khPropagate.page 分页对象
	 * @return
	 */
	@Override
	public Page<KhPropagate> findPage(KhPropagate khPropagate) {
		return super.findPage(khPropagate);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khPropagate
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhPropagate khPropagate) {
		super.save(khPropagate);
	}
	
	/**
	 * 更新状态
	 * @param khPropagate
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhPropagate khPropagate) {
		super.updateStatus(khPropagate);
	}
	
	/**
	 * 删除数据
	 * @param khPropagate
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhPropagate khPropagate) {
		super.delete(khPropagate);
	}


}