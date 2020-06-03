/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.information.service.zx;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.information.dao.zx.ZiXunDao;
import com.jeesite.modules.bright.information.entity.zx.ZiXun;
import com.jeesite.modules.file.utils.FileUploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 内容资讯Service
 * @author liqingfeng
 * @version 2019-08-07
 */
@Service
@Transactional(readOnly=true)
public class ZiXunService extends CrudService<ZiXunDao, ZiXun> {
	
	/**
	 * 获取单条数据
	 * @param ziXun
	 * @return
	 */
	@Override
	public ZiXun get(ZiXun ziXun) {
		return super.get(ziXun);
	}
	
	/**
	 * 查询分页数据
	 * @param ziXun 查询条件
	 * @param ziXun.page 分页对象
	 * @return
	 */
	@Override
	public Page<ZiXun> findPage(ZiXun ziXun) {
		return super.findPage(ziXun);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param ziXun
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZiXun ziXun) {
		FileUploadUtils.saveFileUpload(ziXun.getId(), "testData_image");
		super.save(ziXun);
	}
	
	/**
	 * 更新状态
	 * @param ziXun
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZiXun ziXun) {
		super.updateStatus(ziXun);
	}
	
	/**
	 * 删除数据
	 * @param ziXun
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZiXun ziXun) {
		super.delete(ziXun);
	}
	
}