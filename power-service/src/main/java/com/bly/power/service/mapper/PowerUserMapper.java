package com.bly.power.service.mapper;

import org.apache.ibatis.annotations.Param;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.power.interfaces.entity.system.PowerUser;

public interface PowerUserMapper extends BaseMapper{
	/**
	 * 根据用户名获取用户信息
	 * @param userName
	 * @return
	 */
	public PowerUser getUserByUserName(String userName);
	
	/**
	 * 删除冻结的用户
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用、冻结用户
	 */
	public int enableOrFreeze(@Param("id")Long id,@Param("status")Long status);
}
