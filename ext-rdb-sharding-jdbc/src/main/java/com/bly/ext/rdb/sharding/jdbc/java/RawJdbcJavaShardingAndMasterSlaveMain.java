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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import com.bly.ext.rdb.sharding.jdbc.java.algorithm.ModuloShardingDatabaseAlgorithm;
import com.bly.ext.rdb.sharding.jdbc.java.algorithm.ModuloShardingTableAlgorithm;
import com.bly.ext.rdb.sharding.jdbc.java.repository.RawJdbcRepository;
import com.bly.ext.rdb.sharding.jdbc.java.util.DataSourceUtil;
import com.google.common.collect.Lists;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;

/**
 * 分库分表 + 读写分离
 *
 * @author wangyi
 */
public class RawJdbcJavaShardingAndMasterSlaveMain {
	public static void main(String[] args) throws SQLException {
		RawJdbcRepository repository = new RawJdbcRepository(getShardingDataSource());
		// repository.initData();
		// repository.queryPrintEqualsSelect();
		// repository.queryPrintInSelect();
		// repository.queryPrintHintSimpleSelect();
		repository.destoryTable();
	}

	private static DataSource getShardingDataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
		shardingRuleConfiguration.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingRuleConfiguration.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
		shardingRuleConfiguration.getBindingTableGroups().add("t_order , t_order_item");
		shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", ModuloShardingDatabaseAlgorithm.class.getName()));
		shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("t_order", ModuloShardingTableAlgorithm.class.getName()));
		shardingRuleConfiguration.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
		// 设置全局键生成器
		//shardingRuleConfiguration.setDefaultKeyGeneratorClass(defaultKeyGeneratorClass);
		return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfiguration,new HashMap<String, Object>(), new Properties());
	}

	private static TableRuleConfiguration getOrderTableRuleConfiguration() {
		TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
		tableRuleConfiguration.setLogicTable("t_order");
		// 将ds_master_0 、ds_slave_0 、ds_slave_1组成一个组ds_0
		tableRuleConfiguration.setActualDataNodes("ds_${0..1}.t_order_${[0,1]}");
		tableRuleConfiguration.setKeyGeneratorColumnName("order_id");
		// 设置当前表ID生成器
		// tableRuleConfiguration.setKeyGeneratorClass(OtherKeyGenerator);
		return tableRuleConfiguration;
	}

	private static TableRuleConfiguration getOrderItemTableRuleConfiguration() {
		TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
		tableRuleConfiguration.setLogicTable("t_order_item");
		tableRuleConfiguration.setActualDataNodes("ds_${0..1}.t_order_item_${[0,1]}");
		return tableRuleConfiguration;
	}

	private static List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
		MasterSlaveRuleConfiguration masterSlaveRuleConfiguration1 = new MasterSlaveRuleConfiguration();
		masterSlaveRuleConfiguration1.setName("ds_0"); // 主从结构组的名称
		masterSlaveRuleConfiguration1.setMasterDataSourceName("demo_ds_master_0");
		masterSlaveRuleConfiguration1.setSlaveDataSourceNames(Arrays.asList("demo_ds_master_0_slave_0", "demo_ds_master_0_slave_1"));

		MasterSlaveRuleConfiguration masterSlaveRuleConfiguration2 = new MasterSlaveRuleConfiguration();
		masterSlaveRuleConfiguration2.setName("ds_1");
		masterSlaveRuleConfiguration2.setMasterDataSourceName("demo_ds_master_1");
		masterSlaveRuleConfiguration2.setSlaveDataSourceNames(Arrays.asList("demo_ds_master_1_slave_0", "demo_ds_master_1_slave_1"));

		return Lists.newArrayList(masterSlaveRuleConfiguration1, masterSlaveRuleConfiguration2);
	}

	private static Map<String, DataSource> createDataSourceMap() {
		final Map<String, DataSource> result = new HashMap<>(6, 1);
		result.put("demo_ds_master_0", DataSourceUtil.createDataSource("demo_ds_master_0"));
		result.put("demo_ds_master_0_slave_0", DataSourceUtil.createDataSource("demo_ds_master_0_slave_0"));
		result.put("demo_ds_master_0_slave_1", DataSourceUtil.createDataSource("demo_ds_master_0_slave_1"));
		result.put("demo_ds_master_1", DataSourceUtil.createDataSource("demo_ds_master_1"));
		result.put("demo_ds_master_1_slave_0", DataSourceUtil.createDataSource("demo_ds_master_1_slave_0"));
		result.put("demo_ds_master_1_slave_1", DataSourceUtil.createDataSource("demo_ds_master_1_slave_1"));

		return result;
	}
}
