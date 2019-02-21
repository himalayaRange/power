package com.bly.power.interfaces.service;

import com.bly.common.interfaces.service.BaseService;
import com.bly.power.interfaces.entity.system.UserEntity;

/**
 * 
 * @Desc   : 用户service
 * @author : Administrator
 * @date   : 2017年8月15日
 */
public interface UserService extends BaseService {

	/**
	 * 根据用户名获取用户信息
	 * @param userName
	 * @return
	 */
	public UserEntity getUserByUserName(String userName);
	
	/**
	 * 删除冻结用户信息
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用、冻结用户
	 */
	public int enableOrFreeze(Long id,Long status);
}
