package com.bly.power.service.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.bly.common.service.mapper.BaseMapper;

/**
 * 
 * @Desc   : 公司管理mapper
 * @author : Administrator
 * @date   : 2017年8月21日
 */
public interface CompanyMapper extends BaseMapper {
	/**
	 * 删除冻结的公司
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用、冻结公司
	 */
	public int enableOrFreeze(@Param("id")Long id,@Param("status")Long status);
	/**
	 * 查询公司
	 * @param paramter
	 * @return
	 */
	public List<Map<String,Object>> selectCompanyListInfo(Map<String,Object> paramter);

}
