/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.vip.service;

import com.jeesite.modules.bright.sp.entity.spb.Spbdy;
import com.jeesite.modules.bright.sp.service.spb.SpbdyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.vip.entity.VipXx;
import com.jeesite.modules.bright.vip.dao.VipXxDao;

/**
 * 会员信息表，定义会员级别名称Service
 * @author 马晓亮
 * @version 2019-07-02
 */
@Service
@Transactional(readOnly=true)
public class VipXxService extends CrudService<VipXxDao, VipXx> {

	@Autowired
	private SpbdyService spbdyService;
	
	/**
	 * 获取单条数据
	 * @param vipXx
	 * @return
	 */
	@Override
	public VipXx get(VipXx vipXx) {
		return super.get(vipXx);
	}
	
	/**
	 * 查询分页数据
	 * @param vipXx 查询条件
	 * @param vipXx.page 分页对象
	 * @return
	 */
	@Override
	public Page<VipXx> findPage(VipXx vipXx) {
		Page<VipXx> vipXxPage = super.findPage(vipXx);
		for (VipXx vipXx1:vipXxPage.getList()){
			Spbdy spbdy = new Spbdy();
			spbdy.setId(vipXx1.getSpbid());
			vipXx1.setSpbdy(spbdyService.get(spbdy));
		}
		return vipXxPage;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param vipXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(VipXx vipXx) {
		super.save(vipXx);
	}
	
	/**
	 * 更新状态
	 * @param vipXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(VipXx vipXx) {
		super.updateStatus(vipXx);
	}
	
	/**
	 * 删除数据
	 * @param vipXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(VipXx vipXx) {
		super.delete(vipXx);
	}
	
}