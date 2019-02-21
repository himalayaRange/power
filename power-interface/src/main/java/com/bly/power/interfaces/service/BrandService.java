package com.bly.power.interfaces.service;

import com.bly.common.interfaces.service.BaseService;
import com.bly.power.interfaces.entity.system.BrandEntity;

/**
 * 
 * @Desc   : 品牌管理service
 * @author : Administrator
 * @date   : 2017年8月22日
 */
public interface BrandService extends BaseService {

	/**
	 * 删除冻结品牌信息
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用、冻结品牌
	 */
	public int enableOrFreeze(Long id,Long status);
	
	/**
	 * 根据ID查询品牌信息
	 * @param id
	 * @return
	 */
	public BrandEntity selectById(Long id);
}
