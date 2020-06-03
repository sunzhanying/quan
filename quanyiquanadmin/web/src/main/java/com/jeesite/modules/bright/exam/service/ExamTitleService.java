/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.service;

import com.jeesite.modules.bright.exam.dao.ExamOptionDao;
import com.jeesite.modules.bright.exam.dao.ExamTitleDao;
import com.jeesite.modules.bright.exam.entity.ExamOption;
import com.jeesite.modules.bright.exam.entity.ExamTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;

/**
 * 提目表Service
 * @author 马晓亮
 * @version 2019-08-05
 */
@Service
@Transactional(readOnly=true)
public class ExamTitleService extends CrudService<ExamTitleDao, ExamTitle> {
	
	@Autowired
	private ExamOptionDao examOptionDao;
	
	/**
	 * 获取单条数据
	 * @param examTitle
	 * @return
	 */
	@Override
	public ExamTitle get(ExamTitle examTitle) {
		ExamTitle entity = super.get(examTitle);
		if (entity != null){
			ExamOption examOption = new ExamOption(entity);
			examOption.setStatus(ExamOption.STATUS_NORMAL);
			entity.setExamOptionList(examOptionDao.findList(examOption));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param examTitle 查询条件
	 * @param examTitle.page 分页对象
	 * @return
	 */
	@Override
	public Page<ExamTitle> findPage(ExamTitle examTitle) {
		return super.findPage(examTitle);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param examTitle
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ExamTitle examTitle) {
		super.save(examTitle);
		// 保存 ExamTitle子表
		for (ExamOption examOption : examTitle.getExamOptionList()){
			if (!ExamOption.STATUS_DELETE.equals(examOption.getStatus())){
				examOption.setTitleId(examTitle);
				if (examOption.getIsNewRecord()){
					examOptionDao.insert(examOption);
				}else{
					examOptionDao.update(examOption);
				}
			}else{
				examOptionDao.delete(examOption);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param examTitle
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ExamTitle examTitle) {
		super.updateStatus(examTitle);
	}
	
	/**
	 * 删除数据
	 * @param examTitle
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ExamTitle examTitle) {
		super.delete(examTitle);
		ExamOption examOption = new ExamOption();
		examOption.setTitleId(examTitle);
		examOptionDao.deleteByEntity(examOption);
	}
	
}