/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.service.others.salesroom;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.setfocus.entity.others.salesroom.Salesroom;
import com.jeesite.modules.bright.setfocus.dao.others.salesroom.SalesroomDao;

/**
 * 门店设置/传播渠道设置Service
 * @author liqingfeng
 * @version 2019-08-23
 */
@Service
@Transactional(readOnly=true)
public class SalesroomService extends CrudService<SalesroomDao, Salesroom> {

	public Salesroom getDefaultRoom() {
		return dao.getDefaultRoom();
	}

	/**
	 * 获取单条数据
	 * @param salesroom
	 * @return
	 */
	@Override
	public Salesroom get(Salesroom salesroom) {
		return super.get(salesroom);
	}
	
	/**
	 * 查询分页数据
	 * @param salesroom 查询条件
	 * @param salesroom.page 分页对象
	 * @return
	 */
	@Override
	public Page<Salesroom> findPage(Salesroom salesroom) {
		return super.findPage(salesroom);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param salesroom
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Salesroom salesroom) {
		if(Salesroom.ISDEFAULT_YES.equals(salesroom.getIsdefault())){//修改为默认
			//先将所有的修改为非默认
			dao.updateDefaultAll();
		}
		super.save(salesroom);
	}
	
	/**
	 * 更新状态
	 * @param salesroom
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Salesroom salesroom) {
		super.updateStatus(salesroom);
	}
	
	/**
	 * 删除数据
	 * @param salesroom
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Salesroom salesroom) {
		super.delete(salesroom);
	}
	
}