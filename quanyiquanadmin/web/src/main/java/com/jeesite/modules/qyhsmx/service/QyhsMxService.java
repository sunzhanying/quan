/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhsmx.service;

import java.util.List;

import com.jeesite.modules.bright.sp.dao.SpXxDao;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.qyhs.dao.QyhsDao;
import com.jeesite.modules.qyhs.entity.Qyhs;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;

/**
 * 权益回收明细Service
 * @author 马晓亮
 * @version 2020-03-25
 */
@Service
@Transactional(readOnly=true)
public class QyhsMxService extends CrudService<QyhsMxDao, QyhsMx> {

	@Autowired
	private QyhsDao qyhsDao;
	@Autowired
	private SpXxDao spXxDao;

	/**
	 * 批量 通过或退回
	 * @param str 数组id
	 * @param type 1 通过 2 退回
	 */
	@Transactional(readOnly=false)
	public void updateTgOrTh(String str, String type) {
		String[] strings = str.split(",");
		//改变的状态
		QyhsMx qyhsMx = new QyhsMx();
		//通过
		if ("1".equals(type)){
			qyhsMx.setZt(QyhsMx.STATUS_CSZ);
		}else { //退回
			qyhsMx.setZt(QyhsMx.STATUS_TH);
		}
		//条件
		for (String string : strings) {
			dao.updateByEntity(qyhsMx, new QyhsMx(string));
		}
	}


	@Override
	@Transactional(readOnly=false)
	public void update(QyhsMx entity) {
		super.update(entity);
		//当卡片全部审核完毕时，修改总单的状态
		QyhsMx qyhsMx = new QyhsMx();
		qyhsMx.setZt(QyhsMx.STATUS_DSH);
		qyhsMx.setQyhsId(entity.getQyhsId());
		if (dao.findCount(qyhsMx) == 0) {
			Qyhs qyhs = qyhsDao.get(new Qyhs(entity.getQyhsId()));
			qyhs.setZt(Qyhs.STATUS_SHWC);
			qyhsDao.update(qyhs);
		}
	}

	/**
	 * 获取单条数据
	 * @param qyhsMx
	 * @return
	 */
	@Override
	public QyhsMx get(QyhsMx qyhsMx) {
		return super.get(qyhsMx);
	}
	
	/**
	 * 查询分页数据
	 * @param qyhsMx 查询条件
	 * @return
	 */
	@Override
	public Page<QyhsMx> findPage(QyhsMx qyhsMx) {
		Page<QyhsMx> qyhsMxPage = super.findPage(qyhsMx);
		qyhsMxPage.getList().forEach(item ->{
			item.setSpXx(spXxDao.get(new SpXx(item.getQyqId())));
		});
		return qyhsMxPage;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param qyhsMx
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(QyhsMx qyhsMx) {
		super.save(qyhsMx);
	}
	
	/**
	 * 更新状态
	 * @param qyhsMx
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(QyhsMx qyhsMx) {
		super.updateStatus(qyhsMx);
	}
	
	/**
	 * 删除数据
	 * @param qyhsMx
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(QyhsMx qyhsMx) {
		super.delete(qyhsMx);
	}

	/**
	 * 批量下载
	 */
	@Transactional(readOnly=false)
	public List<QyhsMx> downloadFile(String str) {
		List<QyhsMx> list = spXxDao.findListForDownload(str);
		return list;
	}

	/**
	 * 获取数量
	 */
	@Transactional(readOnly=false)
	public long getQyCount(String kh,String km) {
		long count = spXxDao.getQyCount(kh,km);
		return count;
	}
	
}