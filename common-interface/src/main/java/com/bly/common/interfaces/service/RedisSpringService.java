package com.bly.common.interfaces.service;

import com.bly.common.support.cache.SpringDataRedisHelper;

public interface RedisSpringService {
	
	/**
	 * 获取spring-data-redis线程对象
	 * @return
	 */
	public SpringDataRedisHelper getSpringDataRedis();
	
}
