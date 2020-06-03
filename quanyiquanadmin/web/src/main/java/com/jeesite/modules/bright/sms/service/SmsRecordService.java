/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.sms.entity.SmsRecord;
import com.jeesite.modules.bright.sms.dao.SmsRecordDao;

/**
 * sms_recordService
 * @author 马晓亮
 * @version 2019-07-25
 */
@Service
@Transactional(readOnly=true)
public class SmsRecordService extends CrudService<SmsRecordDao, SmsRecord> {


	public SmsRecord getSmsRecord(SmsRecord smsRecord) {
		return dao.getSmsRecord(smsRecord);
	}


	/**
	 * 获取单条数据
	 * @param smsRecord
	 * @return
	 */
	@Override
	public SmsRecord get(SmsRecord smsRecord) {
		return super.get(smsRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param smsRecord 查询条件
	 * @param smsRecord.page 分页对象
	 * @return
	 */
	@Override
	public Page<SmsRecord> findPage(SmsRecord smsRecord) {
		return super.findPage(smsRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param smsRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SmsRecord smsRecord) {
		super.save(smsRecord);
	}
	
	/**
	 * 更新状态
	 * @param smsRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SmsRecord smsRecord) {
		super.updateStatus(smsRecord);
	}
	
	/**
	 * 删除数据
	 * @param smsRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SmsRecord smsRecord) {
		super.delete(smsRecord);
	}
	
}