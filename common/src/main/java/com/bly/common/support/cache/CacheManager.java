package com.bly.common.support.cache;

import java.io.Serializable;
import java.util.Set;

/**
 * 缓存管理器
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月29日 上午9:54:57 
 * Created by Wang.Yi
 */
public interface CacheManager {
	
	/**
	 * 通过key获取对象
	 * @param key
	 * @return
	 */
	public Object get(final String key);
	/**
	 * 正则表达式获取集合
	 * @param pattern
	 * @return
	 */
	public Set<Object> getAll(final String pattern);
	/**
	 * 设置缓存并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void set(final String key, final Serializable value, int seconds);
	/**
	 * 设置缓存并设置默认时间
	 * @param key
	 * @param value
	 */
	public void set(final String key, final Serializable value);
	/**
	 * 判断某个键是否存在
	 * @param key
	 * @return
	 */
	public Boolean exists(final String key);
	/**
	 * 删除某个键
	 * @param key
	 */
	public void del(final String key);
	/**
	 * 正则表达式删除键集合
	 * @param pattern
	 */
	public void delAll(final String pattern);
	/**
	 * 判断类型
	 * @param key
	 * @return
	 */
	public String type(final String key);
	/**
	 * 某段时间后失效
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Boolean expire(final Object key, final int seconds);
	/**
	 * 某个时间点失效
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public Boolean expireAt(final Object key, final long unixTime);
	/**
	 * 剩余过期时间（单位/秒）
	 * @param key
	 * @return
	 */
	public Long ttl(final String key);
	/**
	 * 先获取再设置
	 * @param key
	 * @param value
	 * @return
	 */
	public Object getSet(final String key, final Serializable value);
	/**
	 * 将 key 的值设为 value，当且仅当 key 不存在。 
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setnx(final String key, final Serializable value);
	/**
	 * 释放锁
	 * @param key
	 */
	public void unlock(String key);
	/**
	 * Set集合中插入键值对
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field, String value);
	/**
	 * Set集合获取某个key的键值对
	 * @param key
	 * @param field
	 * @return
	 */
	public Object hget(String key, String field);
	/**
	 * Set集合中删除某个键
	 * @param key
	 * @param field
	 */
	public void hdel(String key, String field);
	
}
