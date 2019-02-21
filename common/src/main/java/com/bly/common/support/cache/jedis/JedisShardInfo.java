package com.bly.common.support.cache.jedis;

import org.apache.commons.lang3.StringUtils;

public class JedisShardInfo extends redis.clients.jedis.JedisShardInfo {

	public JedisShardInfo(String host, int port) {
        super(host, port);
    }
    
    public void setPassword(String password) {
        if (StringUtils.isNotBlank(password)) {
            super.setPassword(password);
        }
    }
}
