/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.vip.service.jb;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.vip.entity.jb.VipJb;
import com.jeesite.modules.bright.vip.dao.jb.VipJbDao;

/**
 * 会员级别Service
 * @author 马晓亮
 * @version 2019-06-26
 */
@Service
@Transactional(readOnly=true)
public class VipJbService extends CrudService<VipJbDao, VipJb> {
	
	/**
	 * 获取单条数据
	 * @param vipJb
	 * @return
	 */
	@Override
	public VipJb get(VipJb vipJb) {
		return super.get(vipJb);
	}
	
	/**
	 * 查询分页数据
	 * @param vipJb 查询条件
	 * @param vipJb.page 分页对象
	 * @return
	 */
	@Override
	public Page<VipJb> findPage(VipJb vipJb) {
		return super.findPage(vipJb);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param vipJb
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(VipJb vipJb) {
		super.save(vipJb);
	}
	
	/**
	 * 更新状态
	 * @param vipJb
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(VipJb vipJb) {
		super.updateStatus(vipJb);
	}
	
	/**
	 * 删除数据
	 * @param vipJb
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(VipJb vipJb) {
		super.delete(vipJb);
	}
	
}