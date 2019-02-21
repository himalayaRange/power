package com.bly.power.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.entity.system.UserEntity;
import com.bly.power.interfaces.service.UserService;
import com.bly.power.service.mapper.UserMapper;

/**
 * 
 * @Desc   : 用户管理实现类
 * @author : Administrator
 * @date   : 2017年8月15日
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return userMapper;
	}

	/**
	 * 根据用户名获取用户信息
	 * @param userName
	 * @return
	 */
	public UserEntity getUserByUserName(String userName){
		return userMapper.getUserByUserName(userName);
	}

	/**
	 * 删除冻结用户信息
	 */
	@Override
	public int deleteById(Long id) {
		return userMapper.deleteById(id);
	}

	/**
	 * 启用、冻结用户
	 */
	@Override
	public int enableOrFreeze(Long id, Long status) {
		return userMapper.enableOrFreeze(id,status);
	}
}
