/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.banner.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.banner.entity.Banner;
import com.jeesite.modules.bright.banner.dao.BannerDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 推荐位Service
 * @author 李金辉
 * @version 2019-07-25
 */
@Service
@Transactional(readOnly=true)
public class BannerService extends CrudService<BannerDao, Banner> {
	
	/**
	 * 获取单条数据
	 * @param banner
	 * @return
	 */
	@Override
	public Banner get(Banner banner) {
		return super.get(banner);
	}
	
	/**
	 * 查询分页数据
	 * @param banner 查询条件
	 * @param banner.page 分页对象
	 * @return
	 */
	@Override
	public Page<Banner> findPage(Banner banner) {
		return super.findPage(banner);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param banner
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Banner banner) {
		super.save(banner);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(banner.getId(), "banner_image");
	}
	
	/**
	 * 更新状态
	 * @param banner
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Banner banner) {
		super.updateStatus(banner);
	}
	
	/**
	 * 删除数据
	 * @param banner
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Banner banner) {
		super.delete(banner);
	}
	
}