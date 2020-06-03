/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.active.service.sign;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.active.entity.sign.ActiveSign;
import com.jeesite.modules.bright.active.dao.sign.ActiveSignDao;

/**
 * 活动 课程 报名表Service
 * @author 李金辉
 * @version 2019-08-06
 */
@Service
@Transactional(readOnly=true)
public class ActiveSignService extends CrudService<ActiveSignDao, ActiveSign> {
	
	/**
	 * 获取单条数据
	 * @param activeSign
	 * @return
	 */
	@Override
	public ActiveSign get(ActiveSign activeSign) {
		return super.get(activeSign);
	}
	
	/**
	 * 查询分页数据
	 * @param activeSign 查询条件
	 * @param activeSign.page 分页对象
	 * @return
	 */
	@Override
	public Page<ActiveSign> findPage(ActiveSign activeSign) {
		return super.findPage(activeSign);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param activeSign
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ActiveSign activeSign) {
		super.save(activeSign);
	}
	
	/**
	 * 更新状态
	 * @param activeSign
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ActiveSign activeSign) {
		super.updateStatus(activeSign);
	}
	
	/**
	 * 删除数据
	 * @param activeSign
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ActiveSign activeSign) {
		super.delete(activeSign);
	}
	
}