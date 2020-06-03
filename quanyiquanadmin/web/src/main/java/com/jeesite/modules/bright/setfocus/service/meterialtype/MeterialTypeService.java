/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.service.meterialtype;

import java.util.List;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.service.TreeService;
import com.jeesite.modules.bright.setfocus.entity.meterialtype.MeterialType;
import com.jeesite.modules.bright.setfocus.dao.meterialtype.MeterialTypeDao;

/**
 * 产品类型Service
 * @author liqingfeng
 * @version 2019-07-05
 */
@Service
@Transactional(readOnly=true)
public class MeterialTypeService extends TreeService<MeterialTypeDao, MeterialType> {
	public MeterialType getLastByParentCode(MeterialType entity) {
		if (StringUtils.isBlank(entity.getParentCode())) {
			entity.setParentCode("0");
		}
		List<MeterialType> m = dao.getLastByParentCode(entity.getCode());
		if(ListUtils.isEmpty(m)){
			return null;
		}
			return m.get(0);
	}




	public List<MeterialType> findAllByCode(String code){
		return dao.findAllByCode(code);
	}


	/**
	 * 获取单条数据
	 * @param meterialType
	 * @return
	 */
	@Override
	public MeterialType get(MeterialType meterialType) {
		return super.get(meterialType);
	}
	
	/**
	 * 查询列表数据
	 * @param meterialType
	 * @return
	 */
	@Override
	public List<MeterialType> findList(MeterialType meterialType) {
		return super.findList(meterialType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param meterialType
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MeterialType meterialType) {
		super.save(meterialType);
	}
	
	/**
	 * 更新状态
	 * @param meterialType
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MeterialType meterialType) {
		super.updateStatus(meterialType);
	}
	
	/**
	 * 删除数据
	 * @param meterialType
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MeterialType meterialType) {
		super.delete(meterialType);
	}
	
}