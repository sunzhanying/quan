/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.service.templet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.push.entity.templet.Xxtsmb;
import com.jeesite.modules.bright.push.dao.templet.XxtsmbDao;

/**
 * 消息推送模板Service
 * @author 李金辉
 * @version 2019-07-18
 */
@Service
@Transactional(readOnly=true)
public class XxtsmbService extends CrudService<XxtsmbDao, Xxtsmb> {
	
	/**
	 * 获取单条数据
	 * @param xxtsmb
	 * @return
	 */
	@Override
	public Xxtsmb get(Xxtsmb xxtsmb) {
		return super.get(xxtsmb);
	}
	
	/**
	 * 查询分页数据
	 * @param xxtsmb 查询条件
	 * @param xxtsmb.page 分页对象
	 * @return
	 */
	@Override
	public Page<Xxtsmb> findPage(Xxtsmb xxtsmb) {
		return super.findPage(xxtsmb);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param xxtsmb
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Xxtsmb xxtsmb) {
		super.save(xxtsmb);
	}
	
	/**
	 * 更新状态
	 * @param xxtsmb
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Xxtsmb xxtsmb) {
		super.updateStatus(xxtsmb);
	}
	
	/**
	 * 删除数据
	 * @param xxtsmb
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Xxtsmb xxtsmb) {
		super.delete(xxtsmb);
	}
	
}