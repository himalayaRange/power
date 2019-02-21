package com.bly.common.support.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RBucket;
import org.redisson.api.RType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.bly.common.utils.InstanceUtil;
import com.bly.common.utils.PropertiesUtil;

/**
 * Redission缓存辅助类 <br>
 * Redission是线程安全的，通过watch</br>
 * 
 * Copyright 2018 CoderDream's Studio All right reserved Created on 2018年1月29日
 * 下午1:27:49 Created by Wang.Yi
 */
public class RedissonHelper implements CacheManager, ApplicationContextAware {
	private RedissonClient redissonClient = null;
	//private Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");
	private Integer EXPIRE = 1800;
	protected ApplicationContext applicationContext;
		
	public Integer getEXPIRE() {
		return EXPIRE;
	}

	public void setEXPIRE(Integer eXPIRE) {
		EXPIRE = eXPIRE;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	// 获取redisson连接
	private RedissonClient getRedis() {
		if (redissonClient == null) {
			synchronized (RedissonHelper.class) {
				if (redissonClient == null) {
					redissonClient = applicationContext.getBean(RedissonClient.class);
				}
			}
		}
		return redissonClient;
	}

	// 获取分布式Object
	private RBucket<Object> getRedisBucket(String key) {
		return getRedis().getBucket(key);
	}

	@Override
	public Object get(String key) {
		RBucket<Object> redisBucket = getRedisBucket(key);
		expire(redisBucket, EXPIRE);
		return redisBucket.get();
	}

	@Override
	public Set<Object> getAll(String pattern) {
		HashSet<Object> set = InstanceUtil.newHashSet();
		Iterable<String> keys = getRedis().getKeys().getKeysByPattern(pattern);
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			set.add(getRedisBucket(key).get());
		}
		return set;
	}

	@Override
	public void set(String key, Serializable value, int seconds) {
		RBucket<Object> redisBucket = getRedisBucket(key);
		redisBucket.set(value);
		expire(redisBucket, seconds);
	}

	@Override
	public void set(String key, Serializable value) {
		RBucket<Object> temp = getRedisBucket(key);
		temp.set(value);
		expire(temp, EXPIRE);
	}

	@Override
	public Boolean exists(String key) {
		RBucket<Object> redisBucket = getRedisBucket(key);
		return redisBucket.isExists();
	}

	@Override
	public void del(String key) {
		getRedis().getKeys().deleteAsync(key);

	}

	@Override
	public void delAll(String pattern) {
		getRedis().getKeys().deleteByPattern(pattern);
	}

	@Override
	public String type(String key) {
		RType type = getRedis().getKeys().getType(key);
		if (type == null) {
			return null;
		}
		return type.getClass().getName();
	}

	@SuppressWarnings("unchecked")
	public Boolean expire(final Object key, final int seconds) {
		if (key != null) {
			return ((RBucket<Object>) key).expire(seconds, TimeUnit.SECONDS);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean expireAt(final Object key, final long unixTime) {
		if (key != null) {
			return ((RBucket<Object>) key).expireAt(new Date(unixTime));
		}
		return null;
	}

	@Override
	public Long ttl(String key) {
		RBucket<Object> redisBucket = getRedisBucket(key);
		return redisBucket.remainTimeToLive();
	}

	@Override
	public Object getSet(String key, Serializable value) {
		RBucket<Object> redisBucket = getRedisBucket(key);
		return redisBucket.getAndSet(value);
	}

	@Override
	public boolean setnx(String key, Serializable value) {
		return getRedis().getLock(key).tryLock();
	}

	@Override
	public void unlock(String key) {
		getRedis().getLock(key).unlock();
	}

	@Override
	public void hset(String key, String field, String value) {
		getRedis().getMap(key).put(field, value);
	}

	@Override
	public Object hget(String key, String field) {
		return getRedis().getMap(key).get(field);
	}

	@Override
	public void hdel(String key, String field) {
		getRedis().getMap(key).remove(field);
	}
	
	public final void multiSet(final Map<String,Object> temps) {
		getRedis().getBuckets().set(temps);
	}

}
