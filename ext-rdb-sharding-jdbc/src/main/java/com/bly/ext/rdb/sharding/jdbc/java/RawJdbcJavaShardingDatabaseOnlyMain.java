/*
 * Copyright 2008-2018 blysy.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package com.bly.ext.rdb.sharding.jdbc.java;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import com.bly.ext.rdb.sharding.jdbc.java.repository.RawJdbcRepository;
import com.bly.ext.rdb.sharding.jdbc.java.util.DataSourceUtil;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;

/**
 * 仅对表做分库
 *
 * @author wangyi
 */
public class RawJdbcJavaShardingDatabaseOnlyMain {
	public static void main(String[] args) throws SQLException {
		RawJdbcRepository repository = new RawJdbcRepository(getShardingDataSource());
		// 初始化数据
		// repository.initData();
		// repository.queryPrintEqualsSelect();
		// repository.queryPrintInSelect();
		// repository.queryPrintHintSimpleSelect();
		repository.destoryTable();
	}
	
	private static DataSource getShardingDataSource() throws SQLException {
		ShardingRuleConfiguration shardingConfig = new ShardingRuleConfiguration();
		shardingConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
		shardingConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "demo_ds_${user_id % 2}"));
		return ShardingDataSourceFactory.createDataSource(createDataSourceMap(),shardingConfig,new HashMap<String,Object>(),new Properties());
	}

	private static TableRuleConfiguration getOrderTableRuleConfiguration(){
		TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
		tableRuleConfiguration.setLogicTable("t_order");
		tableRuleConfiguration.setKeyGeneratorColumnName("order_id");
		return tableRuleConfiguration;
	}
	
	private static TableRuleConfiguration getOrderItemTableRuleConfiguration(){
		TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
		tableRuleConfiguration.setLogicTable("t_order_item");
		return tableRuleConfiguration;
	}
	
	private static Map<String, DataSource> createDataSourceMap(){
		// 初始容量 、装载因子
		Map<String, DataSource> result = new HashMap<String, DataSource>(2,1);
		result.put("demo_ds_0", DataSourceUtil.createDataSource("demo_ds_0"));
		result.put("demo_ds_1", DataSourceUtil.createDataSource("demo_ds_1"));
		return result;
	}
}
