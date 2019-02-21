package com.bly.power.interfaces.service;

import com.bly.common.interfaces.service.BaseService;

/**
 * 
 * @Desc   : 菜单管理
 * @author : Administrator
 * @date   : 2017年8月24日
 */
public interface MenuService extends BaseService {
	/**
	 * 删除冻结菜单信息
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用、冻结菜单
	 */
	public int enableOrFreeze(Long id,Long status);
}
