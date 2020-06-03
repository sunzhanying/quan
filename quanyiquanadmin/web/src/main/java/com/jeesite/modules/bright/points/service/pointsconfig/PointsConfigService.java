/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.points.service.pointsconfig;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.points.entity.pointsconfig.PointsConfig;
import com.jeesite.modules.bright.points.dao.pointsconfig.PointsConfigDao;

/**
 * t_jf_configService
 * @author 李金辉
 * @version 2019-08-14
 */
@Service
@Transactional(readOnly=true)
public class PointsConfigService extends CrudService<PointsConfigDao, PointsConfig> {
	
	/**
	 * 获取单条数据
	 * @param pointsConfig
	 * @return
	 */
	@Override
	public PointsConfig get(PointsConfig pointsConfig) {
		return super.get(pointsConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param pointsConfig 查询条件
	 * @param pointsConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<PointsConfig> findPage(PointsConfig pointsConfig) {
		return super.findPage(pointsConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param pointsConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PointsConfig pointsConfig) {
		super.save(pointsConfig);
	}
	
	/**
	 * 更新状态
	 * @param pointsConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PointsConfig pointsConfig) {
		super.updateStatus(pointsConfig);
	}
	
	/**
	 * 删除数据
	 * @param pointsConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PointsConfig pointsConfig) {
		super.delete(pointsConfig);
	}
	
}