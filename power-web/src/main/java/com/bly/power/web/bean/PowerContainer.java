package com.bly.power.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.bly.common.support.cache.SpringDataRedisHelper;
import com.bly.power.interfaces.service.RestService;
import com.bly.power.web.constant.WebConstant;
import com.google.common.collect.Maps;

/**
 * 
 * @author wangyi
 */
public class PowerContainer implements InitializingBean {

	@Autowired
	private RestService restService;

	@Autowired
	private SpringDataRedisHelper springService;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 查询所有系统的所有角色信息
		List<Map<String, Object>> list = restService.queryTotalSysRole();
		for (Map<String, Object> sr : list) {
			Object sc = sr.get("sysCode");
			Object rc = sr.get("roleCode");
			if(sc == null || rc == null){
				continue;
			}
			String sysCode = (String)sc;
			String roleCode = (String)rc;
			Map<String, Object> reqMap = Maps.newHashMap();
			reqMap.put("sysCode", sysCode);
			reqMap.put("roleCode", roleCode);
			List<Serializable> roleInfo = restService.getSystemMenuPermissions(reqMap);
			String sysRoleKey  =  WebConstant.PREFIX_POWER_KEY + roleCode + "_" + sysCode;
			springService.leftPushList(sysRoleKey, roleInfo);
		}
	}
}
