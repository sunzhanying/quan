/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyjg.dao.QyjgDao;
import com.jeesite.modules.qyjg.entity.Qyjg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.dao.SpXxDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

import java.util.List;

/**
 * 商品信息Service
 * @author 马晓亮
 * @version 2019-06-25
 */
@Service
@Transactional(readOnly=true)
public class SpXxService extends CrudService<SpXxDao, SpXx> {

	@Autowired
	private QyjgDao qyjgDao;
	@Autowired
	private QyhsMxDao qyhsMxDao;

	/**
	 * 查询商品列表
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<SpXx> findAllList(Integer page, Integer size, String typeid, String name){
		PageHelper.startPage(page,size);
		return new PageInfo<>(dao.findAllList(typeid, name));
	}
	
	/**
	 * 获取单条数据
	 * @param spXx
	 * @return
	 */
	@Override
	public SpXx get(SpXx spXx) {
		return super.get(spXx);
	}

	/**
	 * 获取单条数据
	 * @return
	 */
	public SpXx getSpXx(String id) {
		SpXx spXx = super.get(id);
		Qyjg qyjg = new Qyjg();
		qyjg.setQyqId(id);
		qyjg.setPageSize(1);
		spXx.setQyjg(qyjgDao.findList(qyjg).get(0));
		//库存数量
		QyhsMx qyhsMx = new QyhsMx();
		qyhsMx.setZt(QyhsMx.STATUS_CSZ);
		qyhsMx.setQyqId(id);
		spXx.setKc((int)qyhsMxDao.findCount(qyhsMx));
		//成交量
		qyhsMx.setZt("");
		qyhsMx.getSqlMap().getWhere().and("zt", QueryType.GT, QyhsMx.STATUS_DFK);
		spXx.setCjl(qyhsMxDao.findCount(qyhsMx));
		return spXx;
	}
	
	/**
	 * 查询分页数据
	 * @param spXx 查询条件
	 * @param spXx.page 分页对象
	 * @return
	 */
	@Override
	public Page<SpXx> findPage(SpXx spXx) {
		Page<SpXx> spXxPage = super.findPage(spXx);
		Qyjg qyjg = new Qyjg();
		QyhsMx qyhsMx = new QyhsMx();
		qyhsMx.setZt(QyhsMx.STATUS_CSZ);
		spXxPage.getList().forEach(item ->{
			//出售及回收价
			qyjg.setQyqId(item.getId());
			qyjg.setPageSize(1);
			List<Qyjg> qyjgList = qyjgDao.findList(qyjg);
			if (qyjgList.size() > 0){
				item.setQyjg(qyjgList.get(0));
			}

			//库存数量
			qyhsMx.setQyqId(item.getId());
			item.setKc((int)qyhsMxDao.findCount(qyhsMx));
		});
		return spXxPage;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param spXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SpXx spXx) {
		super.save(spXx);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(spXx.getId(), "spXx_image");
	}
	
	/**
	 * 更新状态
	 * @param spXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SpXx spXx) {
		super.updateStatus(spXx);
	}
	
	/**
	 * 删除数据
	 * @param spXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SpXx spXx) {
		super.delete(spXx);
	}
	
}