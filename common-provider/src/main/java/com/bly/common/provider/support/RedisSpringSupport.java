package com.bly.common.provider.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.provider.mapper.RedisSpringMapper;
import com.bly.common.support.cache.SpringDataRedisHelper;
/**
 * Redis Service Support
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年2月24日 下午2:13:50 
 * Created by Wang.Yi
 */
@Service("redisSpringSupport")
public class RedisSpringSupport implements RedisSpringMapper {
	@Autowired
	private SpringDataRedisHelper springDataRedisHelper;

	@Override
	public SpringDataRedisHelper getSpringDataRedis() {
		
		return springDataRedisHelper;
	}
}
