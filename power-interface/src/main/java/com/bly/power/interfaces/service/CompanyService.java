package com.bly.power.interfaces.service;

import java.util.List;
import java.util.Map;

import com.bly.common.interfaces.service.BaseService;

/**
 * 
 * @Desc   : 公司管理接口层
 * @author : Administrator
 * @date   : 2017年8月21日
 */
public interface CompanyService extends BaseService {

	/**
	 * 删除冻结公司信息
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用、冻结公司
	 */
	public int enableOrFreeze(Long id,Long status);
	
	/**
	 * 查询公司列表信息
	 * @return
	 */
	public List<Map<String,Object>> selectCompanyListInfo(Map<String,Object> paramter);
}
