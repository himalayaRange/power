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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;

/**
 *
 * @author wangyi
 */
public class JavaShardingJdbc {

	public static void main(String[] args) {
		// 配置真实数据源
		Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
		
		// 配置第一个数据源
		DruidDataSource dataSource1 = new DruidDataSource();
		dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource1.setUrl("jdbc:mysql://localhost:3306/ds_1");
		dataSource1.setUsername("root");
		dataSource1.setPassword("root");
		dataSourceMap.put("ds_1", dataSource1);

		// 配置第二个数据源
		DruidDataSource dataSource2 = new DruidDataSource();
		dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource2.setUrl("jdbc:mysql://localhost:3306/ds_2");
		dataSource2.setUsername("root");
		dataSource2.setPassword("root");
		dataSourceMap.put("ds_2", dataSource2);

		// 配置Order规则
		TableRuleConfiguration orderTableRuleConfig  = new TableRuleConfiguration();
		orderTableRuleConfig.setLogicTable("t_order");
		orderTableRuleConfig.setActualDataNodes("ds_${0..1}.t_order_${0..1}");
		
		// 设置分库策略
		orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
		
		// 设置分表策略
		orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));
		
		// 配置分片规则
		ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
		shardingRuleConfiguration.getTableRuleConfigs().add(orderTableRuleConfig);
		
		// 获取数据源对象
		try {
			DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new ConcurrentHashMap<String,Object>(), new Properties());
			String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";
			Connection connection = dataSource.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, 1);
			prepareStatement.setInt(2, 100);
			ResultSet resultSet = prepareStatement.getResultSet();
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1));
				System.out.println(resultSet.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
