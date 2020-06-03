/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khvipcard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.khvipcard.entity.KhVipcard;
import com.jeesite.modules.bright.khvipcard.dao.KhVipcardDao;

import java.util.List;

/**
 * 用户会员卡Service
 * @author 马晓亮
 * @version 2019-08-16
 */
@Service
@Transactional(readOnly=true)
public class KhVipcardService extends CrudService<KhVipcardDao, KhVipcard> {

	/**
	 * 查询客户会员卡
	 * @param khid
	 * @param zt
	 * @return
	 */
	public List<KhVipcard> findByKhidByZt(String khid, String zt){
		return dao.findByKhidByZt(khid, zt);
	}

	/**
	 * 获取单条数据
	 * @param khVipcard
	 * @return
	 */
	@Override
	public KhVipcard get(KhVipcard khVipcard) {
		return super.get(khVipcard);
	}
	
	/**
	 * 查询分页数据
	 * @param khVipcard 查询条件
	 * @param khVipcard.page 分页对象
	 * @return
	 */
	@Override
	public Page<KhVipcard> findPage(KhVipcard khVipcard) {
		return super.findPage(khVipcard);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khVipcard
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhVipcard khVipcard) {
		super.save(khVipcard);
	}
	
	/**
	 * 更新状态
	 * @param khVipcard
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhVipcard khVipcard) {
		super.updateStatus(khVipcard);
	}
	
	/**
	 * 删除数据
	 * @param khVipcard
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhVipcard khVipcard) {
		super.delete(khVipcard);
	}
	
}