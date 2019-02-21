package com.bly.common.support.cache.jedis;

import redis.clients.jedis.ShardedJedis;

public interface Executor<K> {

	public K execute(ShardedJedis jedis);
	
}
