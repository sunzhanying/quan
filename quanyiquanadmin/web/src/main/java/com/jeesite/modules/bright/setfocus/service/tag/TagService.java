/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.service.tag;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import com.jeesite.modules.bright.setfocus.dao.tag.TagDao;

/**
 * 标签Service
 * @author liqingfeng
 * @version 2019-07-05
 */
@Service
@Transactional(readOnly=true)
public class TagService extends CrudService<TagDao, Tag> {
	/**
	 * 获取单条数据
	 * @return
	 */
	public List<Tag> getListByTag(String sort) {
		return dao.getListByTag(sort);
	}

	/**
	 * 获取单条数据
	 * @param tag
	 * @return
	 */
	@Override
	public Tag get(Tag tag) {
		return super.get(tag);
	}
	/**
	 * 获取最大排序
	 * @return
	 */
	public Integer getLastBySort() {
		return dao.getLastBySort();
	}
	
	/**
	 * 查询分页数据
	 * @param tag 查询条件
	 * @return
	 */
	@Override
	public Page<Tag> findPage(Tag tag) {
		return super.findPage(tag);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tag
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Tag tag) {
		super.save(tag);
	}
	
	/**
	 * 更新状态
	 * @param tag
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Tag tag) {
		super.updateStatus(tag);
	}
	
	/**
	 * 删除数据
	 * @param tag
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Tag tag) {
		super.delete(tag);
	}
	
}