/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.service.sptype;


import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.sp.dao.sptype.SpLogDao;
import com.jeesite.modules.bright.sp.entity.sptype.SpLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品日志
 */
@Service
@Transactional(readOnly=true)
public class SpLogService extends CrudService<SpLogDao, SpLog> {


	/**
	 * 保存数据（插入或更新）
	 * @param spLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SpLog spLog) {
		super.save(spLog);
	}
	

}