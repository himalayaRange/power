package com.bly.power.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.service.RestService;
import com.bly.power.service.mapper.RestMapper;
@Service("restService")
public class RestServiceImpl extends BaseServiceImpl implements RestService{
	
	@Autowired
	private RestMapper restMapper; 
	
	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return restMapper;
	}

	@Override
	public List<Serializable> getSystemMenuPermissions(Map<String, Object> paramter) {
		return restMapper.getRoleMenuPermissions(paramter);
	}

	@Override
	public List<Map<String, Object>> queryTotalSysRole() {
		return restMapper.queryTotalSysRole();
	}

}
