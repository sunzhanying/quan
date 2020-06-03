/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.yhdz.service;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.yhdz.entity.KhDz;
import com.jeesite.modules.bright.yhdz.dao.KhDzDao;

/**
 * 用户地址表Service
 * @author 马晓亮
 * @version 2019-07-12
 */
@Service
@Transactional(readOnly=true)
public class KhDzService extends CrudService<KhDzDao, KhDz> {
	
	/**
	 * 获取单条数据
	 * @param khDz
	 * @return
	 */
	@Override
	public KhDz get(KhDz khDz) {
		return super.get(khDz);
	}
	
	/**
	 * 查询分页数据
	 * @param khDz 查询条件
	 * @param khDz.page 分页对象
	 * @return
	 */
	@Override
	public Page<KhDz> findPage(KhDz khDz) {
		return super.findPage(khDz);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khDz
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhDz khDz) {
		//地址为默认时更新其他地址
		if (!Strings.isNullOrEmpty(khDz.getIsDefault()) && KhDz.ISDEFAULF_YES.equals(khDz.getIsDefault())){
			dao.updateIsDefault(khDz.getUserId());
		} else{
			khDz.setIsDefault(KhDz.ISDEFAULF_NO);
		}
		super.save(khDz);
	}
	
	/**
	 * 更新状态
	 * @param khDz
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhDz khDz) {
		super.updateStatus(khDz);
	}
	
	/**
	 * 删除数据
	 * @param khDz
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhDz khDz) {
		super.delete(khDz);
	}
	
}