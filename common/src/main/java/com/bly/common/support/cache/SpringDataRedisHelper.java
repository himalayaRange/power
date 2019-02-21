package com.bly.common.support.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import com.bly.common.utils.InstanceUtil;
/**
 * spring-data-redis缓存辅助类
 * 	1.使用 spring-data-redis替换reidis客户端，解决不同客户端转化的兼容性问题
 *  2.项目本身是基于spring,集成方便，稳定，管理起来更加自动化
 *  3.自动化管理连接池，提供了一个高度封装的redisTemplate类
 *  4.针对jedis客户端的大量API进行归类分装，同一类型操作封装成operation接口
 *  5.针对序列化和反序列化，提供了多种可以选择的策略
 *     JdkSerializationRedisSerializer：当需要存储java对象时使用.
 *     StringRedisSerializer：当需要存储string类型的字符串时使用.
 *     JacksonJsonRedisSerializer：将对象序列化成json的格式存储在redis中
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月29日 上午10:03:12 
 * Created by Wang.Yi
 */
public class SpringDataRedisHelper implements CacheManager , ApplicationContextAware{
	private RedisTemplate<Serializable, Serializable> redisTemplate = null;
	protected ApplicationContext applicationContext ;
	private Integer EXPIRE = 1800;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public Integer getEXPIRE() {
		return EXPIRE;
	}

	public void setEXPIRE(Integer eXPIRE) {
		EXPIRE = eXPIRE;
	}

	// 获取连接，使用懒汉式+锁机制从容器中获取连接对象
	@SuppressWarnings("unchecked")
	private RedisTemplate<Serializable , Serializable> getRedis() {
		if(redisTemplate == null) {
			synchronized (SpringDataRedisHelper.class) {
				if(redisTemplate == null) {
					redisTemplate  = (RedisTemplate<Serializable, Serializable>)applicationContext.getBean("redisTemplate");
				}
			}
		}
		return redisTemplate;
	}

	@Override
	public Object get(String key) {
		expire(key, EXPIRE);
		return redisTemplate.boundValueOps(key).get();
	}

	@Override
	public Set<Object> getAll(String pattern) {
		Set<Object> values = InstanceUtil.newHashSet();
		Set<Serializable> keys = getRedis().keys(pattern);
		for(Serializable key : keys) {
			expire(key.toString(), EXPIRE);
			values.add(redisTemplate.opsForValue().get(key));
		}
		return values;
	}

	@Override
	public void set(String key, Serializable value, int seconds) {
		getRedis().boundValueOps(key).set(value);
		expire(key, seconds);
	}

	@Override
	public void set(String key, Serializable value) {
		getRedis().boundValueOps(key).set(value);
		expire(key, EXPIRE);
	}

	@Override
	public Boolean exists(String key) {
		return getRedis().hasKey(key);
	}

	@Override
	public void del(String key) {
		getRedis().delete(key);
	}

	@Override
	public void delAll(String pattern) {
		getRedis().delete(getRedis().keys(pattern));
	}

	@Override
	public String type(String key) {
		expire(key, EXPIRE);
		return getRedis().type(key).getClass().getName();
	}

	@Override
	public Boolean expire(Object key, final int seconds) {
		if(key != null ) {
			return getRedis().expire((String)key, seconds , TimeUnit.SECONDS);
		}
		return null;
	}

	@Override
	public Boolean expireAt(Object key, final long unixTime) {
		if(key != null ) {
			return getRedis().expireAt((String)key, new Date(unixTime));
		}
		return null;
	}

	@Override
	public Long ttl(String key) {
		return getRedis().getExpire(key , TimeUnit.SECONDS);
	}

	@Override
	public Object getSet(String key, Serializable value) {
		return getRedis().boundValueOps(key).getAndSet(value);
	}

	@Override
	public boolean setnx(String key, Serializable value) {
		/* SET IF NOT EXISTS 设置key为value当且仅当key不存在时候，如果已经存在不做任何动作 ，可以实现分布式锁 */
		return getRedis().boundValueOps(key).setIfAbsent(value);
	}

	@Override
	public void unlock(String key) {
		del(key);
	}

	@Override
	public void hset(String key, String field, String value) {
		getRedis().boundHashOps(key).put(field, value);
	}

	@Override
	public Object hget(String key, String field) {
		return getRedis().boundHashOps(key).get(field);
	}

	@Override
	public void hdel(String key, String field) {
		getRedis().boundHashOps(key).delete(field);
	}
	
	/**
	 * 向redis中插入key中某个位置某个值
	 * @param key
	 * @param location
	 * @param value
	 */
	public void hlist(String key, long index , Serializable value) {
		getRedis().boundListOps(key).set(index, value);
	}
	
	/**
	 * 获取list中某个位置的值
	 * @param key
	 * @param index
	 * @return
	 */
	public Object glist(String key , long index) {
		return getRedis().boundListOps(key).index(index);
	}
	
	/**
	 * LEFT PUSH
	 * @param key
	 * @param value
	 */
	public void lPush(String key , Serializable  value) {
		getRedis().boundListOps(key).leftPush(value);
	}
	
	/**
	 * RIGHT PUSH
	 * @param key
	 * @param value
	 */
	public void rPush(String key , Serializable  value) {
		getRedis().boundListOps(key).rightPush(value);
	}
	
	/**
	 * LEFT POP
	 * @param key
	 * @param value
	 */
	public Object lPop(String key) {
		return getRedis().boundListOps(key).leftPop();
	}
	
	/**
	 * RIGTH POP
	 * @param key
	 * @return
	 */
	public Object rPop(String key) {
		return getRedis().boundListOps(key).rightPop();
	}
	
	/**
	 * left push 集合
	 * @param key
	 * @param value
	 * @return
	 */
	public Long leftPushList(String key , Collection<Serializable>  value){
		Long result = getRedis().opsForList().leftPushAll(key, value);
		return result;
	}
	
	/**
	 * right push 集合
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rightPushList(String key , Collection<Serializable> value){
		Long result = getRedis().opsForList().rightPushAll(key, value);
		return result;
	}
	
	/**
	 * left pop 集合
	 * @param key
	 * @return
	 */
	public Serializable leftPopList(String key){
		return getRedis().opsForList().leftPop(key);
	}
	
	/**
	 * right pop 集合
	 * @param key
	 * @return
	 */
	public Serializable rightPopList(String key){
		return getRedis().opsForList().rightPop(key);
	}
	
	/**
	 * 插入Map 集合
	 * @param key
	 * @param m
	 */
	public void putAllMap(String key ,  Map<? extends Object, ? extends Object> m){
		getRedis().opsForHash().putAll(key, m);
	}
	
	/**
	 * 通过key获取Map 集合
	 * @param key
	 * @return
	 */
	public Map<? extends Object , ? extends Object> getMap(String key){
	    return getRedis().opsForHash().entries(key);
	}
	
	/**
	 * 通过key获取Map集合所有的keys
	 * @param key
	 * @return
	 */
	public Set<? extends Object> getMapKeys(String key){
		return getRedis().opsForHash().keys(key);
	}
	
	/**
	 * 通过key获取Map集合所有的values
	 * @param key
	 * @return
	 */
	public List<Object> getMapValues(String key){
		return getRedis().opsForHash().values(key);
	}
	
}
