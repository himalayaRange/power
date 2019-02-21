package com.bly.common.config;

import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.fastjson.JSON;
import com.bly.common.Constants;

/**
 * Redis Configuration
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月22日 下午1:22:44 
 * Created by Wang.Yi
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport{

	/**
	 * 自定义缓存KEY生成策略
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			
			@Override
			public Object generate(Object target, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder(Constants.CACHE_NAMESPACE);
				CacheConfig cacheConfig = target.getClass().getAnnotation(CacheConfig.class);
				Cacheable cacheable = method.getAnnotation(Cacheable.class);
				CachePut cachePut = method.getAnnotation(CachePut.class);
				CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
				if (cacheable != null) {
					String[] cacheNames  = cacheable.value();
					if(cacheNames != null && cacheNames.length >0) {
						sb.append(cacheNames[0]);
					}
				}else if (cachePut != null) {
					String[] cacheNames = cachePut.value();
					if (cacheNames != null && cacheNames.length > 0) {
						sb.append(cacheNames[0]);
					}
				} else if (cacheEvict != null) {
					String[] cacheNames = cacheEvict.value();
					if (cacheNames != null && cacheNames.length > 0) {
						sb.append(cacheNames[0]);
					}
				}
				if (cacheConfig != null && sb.toString().equals(Constants.CACHE_NAMESPACE)) {
					String[] cacheNames = cacheConfig.cacheNames();
					if (cacheNames != null && cacheNames.length > 0) {
						sb.append(cacheNames[0]);
					}
				}
				if (sb.toString().equals(Constants.CACHE_NAMESPACE)) {
					sb.append(target.getClass().getName());
				}
				sb.append(":");
				if (objects != null) {
					if (objects.length == 1) {
						if (objects[0] instanceof Number || objects[0] instanceof String
								|| objects[0] instanceof Boolean) {
							sb.append(objects[0]);
						} else {
							try {
								sb.append(objects[0].getClass().getMethod("getId").invoke(objects[0]));
							} catch (Exception e) {
								if (objects[0] instanceof Map && ((Map<?, ?>) objects[0]).get("id") != null) {
									sb.append(((Map<?, ?>) objects[0]).get("id"));
								} else {
									sb.append(JSON.toJSON(objects[0]));
								}
							}
						}
					} else {
						sb.append(StringUtils.join(objects, ","));
					}
				} else {
					sb.append(method.getName());
				}
				return sb.toString();
			}
		};
	}
	
}
