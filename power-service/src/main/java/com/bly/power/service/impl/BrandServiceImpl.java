package com.bly.power.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.entity.system.BrandEntity;
import com.bly.power.interfaces.service.BrandService;
import com.bly.power.service.mapper.BrandMapper;

/**
 * 
 * @Desc : 品牌管理
 * @author : Administrator
 * @date : 2017年8月22日
 */
@Service("brandService")
public class BrandServiceImpl extends BaseServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;

	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return brandMapper;
	}

	/**
	 * 删除冻结品牌信息
	 */
	@Override
	public int deleteById(Long id) {
		return brandMapper.deleteById(id);
	}

	/**
	 * 启用、冻结品牌
	 */
	@Override
	public int enableOrFreeze(Long id, Long status) {
		return brandMapper.enableOrFreeze(id,status);
	}

	/**
	 * 根据ID查询品牌信息
	 */
	@Override
	public BrandEntity selectById(Long id) {

		return brandMapper.selectById(id);
	}

}
