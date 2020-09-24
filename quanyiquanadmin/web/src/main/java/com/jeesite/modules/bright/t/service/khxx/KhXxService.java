/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.service.khxx;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.util.TokenUtils;
import com.jeesite.API.weixin.bean.sns.SnsToken;
import com.jeesite.API.zyapi.BaseResult;
import com.jeesite.API.zyapi.MemberResult;
import com.jeesite.API.zyapi.ZyAPI;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.content.dao.meterail.MeterialDao;
import com.jeesite.modules.bright.sms.dao.SmsRecordDao;
import com.jeesite.modules.bright.sp.dao.SpXxDao;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.dao.khxx.XsXxDao;
import com.jeesite.modules.bright.t.dao.propagate.KhPropagateDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.entity.khxx.XsXx;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.sale.entity.Sale;
import com.jeesite.modules.sale.service.SaleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户信息Service
 * @author 李金辉
 * @version 2019-06-24
 */
@Service
@Transactional(readOnly=true)
public class KhXxService extends CrudService<KhXxDao, KhXx> {
	
	@Autowired
	private XsXxDao xsXxDao;
	@Autowired
	private KhXxDao khXxDao;
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private KhPropagateDao khPropagateDao;
	@Autowired
	private SpXxDao spXxDao;
	@Autowired
	private MeterialDao meterialDao;

	@Autowired
	private SmsRecordDao smsRecordDao;

	@Autowired
	private KhXxService khXxService;
	@Autowired
	private ZyAPI zyAPI;

	@Value("${weixin.config_id}")
	private String wxConfigId;

	@Autowired
	private SaleService saleService;

	/**
	 * 获取单条数据
	 * @param khXx
	 * @return
	 */
	@Override
	public KhXx get(KhXx khXx) {
		KhXx entity = super.get(khXx);
		if (entity != null){
			XsXx xsXx = new XsXx(entity);
			xsXx.setStatus(XsXx.STATUS_NORMAL);
			entity.setXsXxList(xsXxDao.findList(xsXx));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param khXx 查询条件
	 * @return
	 */
	@Override
	public Page<KhXx> findPage(KhXx khXx) {
		return super.findPage(khXx);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhXx khXx) {
		super.save(khXx);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(khXx.getId(), "khXx_image");
		/*// 保存上传附件
		FileUploadUtils.saveFileUpload(khXx.getId(), "khXx_file");*/

		// 保存 KhXx子表
		for (XsXx xsXx : khXx.getXsXxList()){
			if (!XsXx.STATUS_DELETE.equals(xsXx.getStatus())){
				xsXx.setKhid(khXx);
				if (xsXx.getIsNewRecord()){
					xsXxDao.insert(xsXx);
				}else{
					xsXxDao.update(xsXx);
				}
			}else{
				xsXxDao.delete(xsXx);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param khXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhXx khXx) {
		super.updateStatus(khXx);
	}
	
	/**
	 * 删除数据
	 * @param khXx
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhXx khXx) {
		super.delete(khXx);
		XsXx xsXx = new XsXx();
		xsXx.setKhid(khXx);
		xsXxDao.deleteByEntity(xsXx);
	}


	@Transactional(readOnly=false)
    public Object login(SnsToken token, String source) {
		KhXx khXx =new KhXx();
		khXx.setOpenId(token.getOpenid());
		khXx.setType(source);
		khXx = khXxDao.getByEntity(khXx);
		//没有注册为新用户
		if(khXx==null){
			khXx =new KhXx();
			khXx.setOpenId(token.getOpenid());
			khXx.setUnionId(token.getUnionid());
			khXx.setStatus(KhXx.STATUS_NORMAL);
			khXx.setType(source);
			khXx.preInsert();

			khXxDao.insert(khXx);
			String jwtToken = tokenUtils.generateToken(khXx.getId() ,"");
			khXx.setToken("Bearer " + jwtToken);
		}
		if(!KhXx.STATUS_NORMAL.equals(khXx.getStatus()) /*||(companyCardsDao.selectByUserId(khXx.getId())!=null && "1".equals(companyCardsDao.selectByUserId(khXx.getId()).getStatus()))*/){
			Map<Object,Object> map=new HashMap<>();
			map.put("error","该账户异常，请联系客服。");
			map.put("token",tokenUtils.generateToken(khXx.getId() , ""));
			return map;
		}
		String jwtToken = tokenUtils.generateToken(khXx.getId() , "");
		khXx.setToken("Bearer " + jwtToken);
		return khXx;
    }

	/**
	 *
	 * @param khXx
	 * @return
	 */
	public BaseResult addMember(KhXx khXx){
		System.out.println("开始注册");
		MemberResult m = new MemberResult();
		m.setNumber(khXx.getId());

		BaseResult baseResult = null;
		try {
			baseResult	= zyAPI.addMember(m);
			System.out.println(baseResult.getMessages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseResult;
	}

	/**
	 * 保存上级和上上级粉丝信息
	 * @param inviteCode
	 * @param khXx
	 * @return
	 */
	@Transactional(readOnly=false)
	public Response checkInviteCode(String inviteCode, KhXx khXx) {
		//根据邀请码查询父1级
		String khidParentOne = khXxDao.getUserIdByCode(inviteCode);
		if(StringUtils.isEmpty(khidParentOne)){
			return new Response(Code.API_PARENT_ONE);
		}
		//校验当前用户是否已经绑定过上家，如果绑定过直接返回
		List<Sale> listCurrent = saleService.getSaleListByKhid(khXx.getId());
		if(listCurrent != null && !listCurrent.isEmpty()){
			return new Response("当前用户已经绑定过邀请码，请勿重复绑定！");
		}

		//如果找到父1级，则保存父1级与当前用户关系，以当前用户为中间节点
		Sale sale = new Sale();
		sale.setKhid(khXx.getId());
		sale.setParentOne(khidParentOne);
		saleService.save(sale);

		//根据父1级找父2级
		List<Sale> list = saleService.getSaleListByKhid(khidParentOne);
		String khidParentTwo = "";
		Sale saleForParentOne = new Sale();
		if(list != null && !list.isEmpty()){
			for(Sale saleEntity :list){
				if(!StringUtils.isEmpty(saleEntity.getParentOne())){
					khidParentTwo = saleEntity.getParentOne();
					saleForParentOne = saleEntity;
					break;
				}
			}
		}

		//如果父2级找不到，直接保存当前用户为父1级的子1级
		if(saleForParentOne == null || StringUtils.isEmpty(saleForParentOne.getId())){
			saleForParentOne.setKhid(khidParentOne);
			saleForParentOne.setChildOne(khXx.getId());
			saleForParentOne.setParentOne(khidParentTwo);
			saleService.save(saleForParentOne);//保存操作
			return new Response(Code.API_SUCCESS_REGISTER);//直接返回
		}

		//如果根据邀请码可以找到父1级，并且子级上没数据，则更新
		if(saleForParentOne != null && !StringUtils.isEmpty(saleForParentOne.getId()) && StringUtils.isEmpty(saleForParentOne.getChildOne())){
			saleForParentOne.setChildOne(khXx.getId());
			saleService.save(saleForParentOne);//更新
		}

		return new Response(Code.API_SUCCESS_REGISTER);
	}
}