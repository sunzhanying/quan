/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.exam.entity.ExamType;
import com.jeesite.modules.bright.exam.dao.ExamTypeDao;
import com.jeesite.modules.bright.exam.entity.ExamConfig;
import com.jeesite.modules.bright.exam.dao.ExamConfigDao;

/**
 * 题目类型表Service
 * @author 马晓亮
 * @version 2019-08-02
 */
@Service
@Transactional(readOnly=true)
public class ExamTypeService extends CrudService<ExamTypeDao, ExamType> {
	
	@Autowired
	private ExamConfigDao examConfigDao;
	
	/**
	 * 获取单条数据
	 * @param examType
	 * @return
	 */
	@Override
	public ExamType get(ExamType examType) {
		ExamType entity = super.get(examType);
		if (entity != null){
			ExamConfig examConfig = new ExamConfig(entity);
			examConfig.setStatus(ExamConfig.STATUS_NORMAL);
			entity.setExamConfigList(examConfigDao.findList(examConfig));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param examType 查询条件
	 * @param examType.page 分页对象
	 * @return
	 */
	@Override
	public Page<ExamType> findPage(ExamType examType) {
		return super.findPage(examType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param examType
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ExamType examType) {
		super.save(examType);
		// 保存 ExamType子表
		for (ExamConfig examConfig : examType.getExamConfigList()){
			if (!ExamConfig.STATUS_DELETE.equals(examConfig.getStatus())){
				examConfig.setTypeid(examType);
				if (examConfig.getIsNewRecord()){
					examConfigDao.insert(examConfig);
				}else{
					examConfigDao.update(examConfig);
				}
			}else{
				examConfigDao.delete(examConfig);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param examType
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ExamType examType) {
		super.updateStatus(examType);
	}
	
	/**
	 * 删除数据
	 * @param examType
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ExamType examType) {
		super.delete(examType);
		ExamConfig examConfig = new ExamConfig();
		examConfig.setTypeid(examType);
		examConfigDao.deleteByEntity(examConfig);
	}
	
}