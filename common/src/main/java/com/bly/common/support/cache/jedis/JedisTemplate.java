 package com.bly.common.support.cache.jedis;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import com.bly.common.utils.InstanceUtil;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
 * ShardedJedisPool是针对redis 2.x版本不支持集群的缺陷，而提供共享连接池的概念
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月26日 下午5:01:40 
 * Created by Wang.Yi
 */
@Component
public class JedisTemplate {
	private static final Logger logger = LoggerFactory.getLogger(JedisTemplate.class);
	
	private static ShardedJedisPool shardedJedisPool  = null;
	
	private static Integer EXPIRE = null;
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory ;
	
	// 获取共享资源线程
	private ShardedJedis getJedis() {
		if(shardedJedisPool == null) {
			synchronized (EXPIRE) {
				if(shardedJedisPool == null) {
					JedisPoolConfig poolConfig = jedisConnectionFactory.getPoolConfig();
					JedisShardInfo shardInfo = jedisConnectionFactory.getShardInfo();
				    ArrayList<JedisShardInfo> list = InstanceUtil.newArrayList(shardInfo);
				    shardedJedisPool = new ShardedJedisPool(poolConfig, list);
				}
			}
		}
		return shardedJedisPool.getResource();
	}
	
	public <K> K run(String key, Executor<K> executor, Integer... expire) {
        ShardedJedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            K result = executor.execute(jedis);
            if (jedis.exists(key)) {
                if (expire == null || expire.length == 0) {
                    jedis.expire(key, EXPIRE);
                } else if (expire.length == 1) {
                    jedis.expire(key, expire[0]);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public <K> K run(byte[] key, Executor<K> executor, Integer... expire) {
        ShardedJedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            K result = executor.execute(jedis);
            if (jedis.exists(key)) {
                if (expire == null || expire.length == 0) {
                    jedis.expire(key, EXPIRE);
                } else if (expire.length == 1) {
                    jedis.expire(key, expire[0]);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
