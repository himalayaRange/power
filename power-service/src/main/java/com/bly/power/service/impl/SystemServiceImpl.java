package com.bly.power.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.service.SystemService;
import com.bly.power.service.mapper.SystemMapper;

/**
 * 
 * @Desc : 系统管理
 * @author : Administrator
 * @date : 2017年8月24日
 */
@Service("sysService")
public class SystemServiceImpl extends BaseServiceImpl implements SystemService {

	@Autowired
	private SystemMapper systemMapper;

	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return systemMapper;
	}

	@Override
	public int deleteById(Long id) {
		return systemMapper.deleteById(id);
	}

	@Override
	public int enableOrFreeze(Long id, Long status) {
		return systemMapper.enableOrFreeze(id,status);
	}

}
