package com.bly.power.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.service.CompanyService;
import com.bly.power.service.mapper.CompanyMapper;

/**
 * 
 * @Desc   : 公司管理实现层
 * @author : Administrator
 * @date   : 2017年8月21日
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;
	
	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return companyMapper;
	}

	/**
	 * 删除冻结的公司
	 */
	@Override
	public int deleteById(Long id) {
		return companyMapper.deleteById(id);
	}

	/**
	 * 启用、冻结公司
	 */
	@Override
	public int enableOrFreeze(Long id,Long status){
		return companyMapper.enableOrFreeze(id,status);
	}

	/**
	 * 查询公司列表
	 */
	@Override
	public List<Map<String, Object>> selectCompanyListInfo(Map<String,Object> paramter) {

		return companyMapper.selectCompanyListInfo(paramter);
	}
	
}
