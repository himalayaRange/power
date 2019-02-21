package com.bly.power.web.sysUtil;

import com.bly.power.interfaces.entity.system.PowerUser;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.redis.DistributedSessionUtil;

public class SysOperationUtil {

	public static Long getCreatorOrUpdator() {
		Object value = DistributedSessionUtil.redisSesssion(WebConstant.POWER_USER_SESSION);
		if(value == null){
			return null; 
		}else{
			try {
				PowerUser activeUser = (PowerUser)value;
				return activeUser.getId();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
