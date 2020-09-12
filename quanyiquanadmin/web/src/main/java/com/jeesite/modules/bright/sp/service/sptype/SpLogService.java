/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.service.sptype;


import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.sp.dao.sptype.SpLogDao;
import com.jeesite.modules.bright.sp.entity.sptype.SpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品日志
 */
@Service
@Transactional(readOnly=true)
public class SpLogService extends CrudService<SpLogDao, SpLog> {

	@Autowired
	private SpLogDao spLogDao;

	/**
	 * 保存数据（插入或更新）
	 * @param spLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SpLog spLog) {
		super.save(spLog);
	}

	@Transactional(readOnly=false)
	public List<SpLog> getLogs(String khid) {
		return spLogDao.getLogs(khid);
	}

	@Transactional(readOnly=false)
	public SpLog getLogsByKhidAndSpid(String khid,String spid) {
		return spLogDao.getLogsByKhidAndSpid(khid,spid);
	}

	

}