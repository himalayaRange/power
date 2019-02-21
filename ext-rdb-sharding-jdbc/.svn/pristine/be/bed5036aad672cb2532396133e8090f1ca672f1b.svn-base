package com.bly.ext.rdb.sharding.jdbc.java.orchestration;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import com.bly.ext.rdb.sharding.jdbc.java.util.DataSourceUtil;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import io.shardingjdbc.orchestration.api.OrchestrationShardingDataSourceFactory;
import io.shardingjdbc.orchestration.api.config.OrchestrationConfiguration;
import io.shardingjdbc.orchestration.reg.api.RegistryCenterConfiguration;
import io.shardingjdbc.orchestration.reg.zookeeper.ZookeeperConfiguration;

/**
 * 
 * @author wangyi
 */
public class OrchestrationSharingJdbc {
	public static void main(String[] args) {
		//DataSource dataSource = OrchestrationShardingDataSourceFactory.createDataSource(createDataSourceMap(), createShardingRuleConfig(), new ConcurrentHashMap<String, Object>(), new Properties(),new OrchestrationConfiguration("orchestration-sharding-data-source", getRegistryCenterConfiguration(), false));
	}

	private static RegistryCenterConfiguration getRegistryCenterConfiguration() {
		ZookeeperConfiguration config = new ZookeeperConfiguration();
		config.setServerLists("192.168.0.60:2181");
		config.setNamespace("orchestration-demo");
		return config;
	}

	private static Map<String, DataSource> createDataSourceMap() {
		// 初始容量 、装载因子
		Map<String, DataSource> result = new HashMap<String, DataSource>(2, 1);
		result.put("demo_ds_0", DataSourceUtil.createDataSource("demo_ds_0"));
		result.put("demo_ds_1", DataSourceUtil.createDataSource("demo_ds_1"));
		return result;
	}
}
