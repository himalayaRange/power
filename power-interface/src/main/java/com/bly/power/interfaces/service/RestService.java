package com.bly.power.interfaces.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.bly.common.interfaces.service.BaseService;
public interface RestService extends BaseService{

	public List<Serializable> getSystemMenuPermissions(Map<String , Object> paramter);
	
	public List<Map<String,Object>> queryTotalSysRole();

}
