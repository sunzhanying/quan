/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.points.service.pointslog;

import java.util.List;

import com.jeesite.modules.bright.points.entity.pointsconfig.PointsConfig;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.points.entity.pointslog.PointsLog;
import com.jeesite.modules.bright.points.dao.pointslog.PointsLogDao;

/**
 * 客户积分流水表Service
 * @author 李金辉
 * @version 2019-08-14
 */
@Service
@Transactional(readOnly=true)
public class PointsLogService extends CrudService<PointsLogDao, PointsLog> {
	@Autowired
	KhXxDao khXxDao;
	/**
	 * 获取单条数据
	 * @param pointsLog
	 * @return
	 */
	@Override
	public PointsLog get(PointsLog pointsLog) {
		return super.get(pointsLog);
	}
	
	/**
	 * 查询分页数据
	 * @param pointsLog 查询条件
	 * @param pointsLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<PointsLog> findPage(PointsLog pointsLog) {
		return super.findPage(pointsLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param pointsLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PointsLog pointsLog) {
		super.save(pointsLog);
	}
	
	/**
	 * 更新状态
	 * @param pointsLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PointsLog pointsLog) {
		super.updateStatus(pointsLog);
	}
	
	/**
	 * 删除数据
	 * @param pointsLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PointsLog pointsLog) {
		super.delete(pointsLog);
	}

	/**
	 * 增加积分

	 */
	@Transactional(readOnly=false)
	public synchronized void savePoints(KhXx khXx, PointsConfig pointConfig, Long points) {
		PointsLog pointsLog =new PointsLog();
		pointsLog.setKhid(khXx.getId());
		pointsLog.setJflx(pointConfig.getId());
		pointsLog.setMc(pointConfig.getMc());
		pointsLog.setSl(points);
		pointsLog.preInsert();
		dao.insert(pointsLog);
		khXx.setJf(khXx.getJf()+points);
		khXx.preUpdate();
		khXxDao.update(khXx);
	}

}