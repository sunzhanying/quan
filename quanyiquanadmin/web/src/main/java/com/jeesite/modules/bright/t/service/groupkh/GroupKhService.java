/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.service.groupkh;

import com.jeesite.modules.bright.t.dao.group.GroupKhDao;
import com.jeesite.modules.bright.t.entity.group.GroupKh;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;


/**
 * 分组用户表Service
 * @author 李金辉
 * @version 2019-07-18
 */
@Service
@Transactional(readOnly=true)
public class GroupKhService extends CrudService<GroupKhDao, GroupKh> {
	
	/**
	 * 获取单条数据
	 * @param groupKh
	 * @return
	 */
	@Override
	public GroupKh get(GroupKh groupKh) {
		return super.get(groupKh);
	}
	
	/**
	 * 查询分页数据
	 * @param groupKh 查询条件
	 * @param groupKh.page 分页对象
	 * @return
	 */
	@Override
	public Page<GroupKh> findPage(GroupKh groupKh) {
		return super.findPage(groupKh);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param groupKh
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(GroupKh groupKh) {
		super.save(groupKh);
	}
	
	/**
	 * 更新状态
	 * @param groupKh
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(GroupKh groupKh) {
		super.updateStatus(groupKh);
	}
	
	/**
	 * 删除数据
	 * @param groupKh
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(GroupKh groupKh) {
		super.delete(groupKh);
	}
	
}