/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhs.service;

import java.util.List;

import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.sp.dao.SpXxDao;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.qyhs.entity.Qyhs;
import com.jeesite.modules.qyhs.dao.QyhsDao;

/**
 * 权益回收Service
 * @author 马晓亮
 * @version 2020-03-25
 */
@Service
@Transactional(readOnly=true)
public class QyhsService extends CrudService<QyhsDao, Qyhs> {

	@Autowired
	private QyhsMxDao qyhsMxDao;
	@Autowired
	private KhXxDao khXxDao;
	@Autowired
	private SpXxDao spXxDao;
	
	/**
	 * 获取单条数据
	 * @param qyhs
	 * @return
	 */
	@Override
	public Qyhs get(Qyhs qyhs) {
		return super.get(qyhs);
	}
	
	/**
	 * 查询分页数据
	 * @param qyhs 查询条件
	 * @param qyhs.page 分页对象
	 * @return
	 */
	@Override
	public Page<Qyhs> findPage(Qyhs qyhs) {
		Page<Qyhs> qyhsPage = super.findPage(qyhs);

		qyhsPage.getList().forEach(item ->{
			item.setKhXx(khXxDao.get(new KhXx(item.getKhid())));
			item.setSpXx(spXxDao.get(new SpXx(item.getQyqId())));
			QyhsMx qyhsMx = new QyhsMx();
			qyhsMx.setQyhsId(item.getId());
			//待审核
			qyhsMx.setZt(QyhsMx.STATUS_DSH);
			item.setDsh((int)qyhsMxDao.findCount(qyhsMx));
			//退回
			qyhsMx.setZt("");
			qyhsMx.setZt(QyhsMx.STATUS_TH);
			item.setTh((int)qyhsMxDao.findCount(qyhsMx));
			//通过
			qyhsMx.setZt("");
			qyhsMx.getSqlMap().getWhere().and("zt", QueryType.GT, QyhsMx.STATUS_TH);
			item.setTg((int)qyhsMxDao.findCount(qyhsMx));
		});
		return qyhsPage;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param qyhs
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Qyhs qyhs) {
		super.save(qyhs);
		for (QyhsMx qyhsMx:qyhs.getQyhsMxes()){
			qyhsMx.setKhid(qyhs.getKhid());
			qyhsMx.setQyhsId(qyhs.getId());
			qyhsMx.setQyqId(qyhs.getQyqId());
			qyhsMx.setType(qyhs.getType());
			qyhsMx.setZt(QyhsMx.STATUS_DSH);
			qyhsMx.preInsert();
			qyhsMxDao.insert(qyhsMx);
		}
	}
	
	/**
	 * 更新状态
	 * @param qyhs
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Qyhs qyhs) {
		super.updateStatus(qyhs);
	}
	
	/**
	 * 删除数据
	 * @param qyhs
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Qyhs qyhs) {
		super.delete(qyhs);
	}

	/**
	 * 获取数量
	 * @return
	 */
	public int countByQyqAndZt(String qyqid) {
		return qyhsMxDao.countByQyqAndZt(qyqid);
	}
	
}