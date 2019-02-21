package com.bly.power.web.redis;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * date : 2017年12月6日
 * 
 * auth : WANGYI
 * 
 */
public class DistributedSessionUtil {

	@SuppressWarnings("unchecked")
	public static synchronized <T> T redisSesssion(final String SESSION_KEY) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		Object value = requestAttributes.getAttribute(SESSION_KEY, RequestAttributes.SCOPE_SESSION);
		if(value == null){
			return null;
		}else{
			T st = (T)value;
			return st;
		}
	}
}
