/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.formid.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.formid.entity.FormId;
import com.jeesite.modules.bright.formid.dao.FormIdDao;

/**
 * a_form_idService
 * @author 李金辉
 * @version 2019-07-19
 */
@Service
@Transactional(readOnly=true)
public class FormIdService extends CrudService<FormIdDao, FormId> {
	
	/**
	 * 获取单条数据
	 * @param formId
	 * @return
	 */
	@Override
	public FormId get(FormId formId) {
		return super.get(formId);
	}
	
	/**
	 * 查询分页数据
	 * @param formId 查询条件
	 * @param formId.page 分页对象
	 * @return
	 */
	@Override
	public Page<FormId> findPage(FormId formId) {
		return super.findPage(formId);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param formId
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FormId formId) {
		super.save(formId);
	}
	
	/**
	 * 更新状态
	 * @param formId
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FormId formId) {
		super.updateStatus(formId);
	}
	
	/**
	 * 删除数据
	 * @param formId
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FormId formId) {
		super.delete(formId);
	}
	
}