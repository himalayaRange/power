package com.bly.power.interfaces.service;

import com.bly.common.interfaces.service.BaseService;

/**
 * 
 * @Desc   : 系统管理service
 * @author : Administrator
 * @date   : 2017年8月24日
 */
public interface SystemService extends BaseService {

	/**
	 * 根据ID删除冻结的平台系统
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用冻结平台系统
	 * @param id
	 */
	public int enableOrFreeze(Long id , Long status);
	
}
