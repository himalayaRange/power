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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import com.bly.ext.rdb.sharding.jdbc.java.repository.RawJdbcRepository;
import com.bly.ext.rdb.sharding.jdbc.java.util.DataSourceUtil;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;

/**
 * 读写分离
 *
 * @author wangyi
 */
public class RawJdbcJavaMasterSlaveOnlyMain {
	public static void main(String[] args) throws SQLException {
		RawJdbcRepository repository = new RawJdbcRepository(getMasterSlaveDataSource());
		// repository.initData();
		 repository.queryPrintEqualsSelect();
		// repository.queryPrintHintSimpleSelect();
		// repository.queryPrintHintSimpleSelect();
		// repository.destoryTable();
	}
	
	private static DataSource getMasterSlaveDataSource() throws SQLException{
		MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration();
		masterSlaveRuleConfiguration.setName("demo_ds_master_slave");
		masterSlaveRuleConfiguration.setMasterDataSourceName("demo_ds_master");
		masterSlaveRuleConfiguration.setSlaveDataSourceNames(Arrays.asList("demo_ds_slave_0" , "demo_ds_slave_1"));
		return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(), masterSlaveRuleConfiguration, new ConcurrentHashMap<String,Object>());
	}
	
	private static final Map<String,DataSource> createDataSourceMap(){
		final Map<String,DataSource> result = new HashMap<String,DataSource>(3,1);
		result.put("demo_ds_master",  DataSourceUtil.createDataSource("demo_ds_master"));
		result.put("demo_ds_slave_0", DataSourceUtil.createDataSource("demo_ds_slave_0"));
		result.put("demo_ds_slave_1", DataSourceUtil.createDataSource("demo_ds_slave_1"));
		return result;
	}
}
