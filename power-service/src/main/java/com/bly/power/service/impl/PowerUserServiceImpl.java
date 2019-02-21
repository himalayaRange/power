package com.bly.power.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.entity.system.PowerUser;
import com.bly.power.interfaces.service.PowerUserService;
import com.bly.power.service.mapper.PowerUserMapper;
@Service("powerUserService")
public class PowerUserServiceImpl extends BaseServiceImpl implements PowerUserService {
	@Autowired
	private PowerUserMapper powerUserMapper;
	
	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return powerUserMapper;
	}

	@Override
	public PowerUser getUserByUserName(String userName) {
		return powerUserMapper.getUserByUserName(userName);
	}

	@Override
	public int deleteById(Long id) {
		return powerUserMapper.deleteById(id);
	}

	@Override
	public int enableOrFreeze(Long id, Long status) {
		return powerUserMapper.enableOrFreeze(id, status);
	}

}
