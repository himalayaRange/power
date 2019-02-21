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
package com.bly.ext.rdb.sharding.jdbc.java.util;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 *
 * @author wangyi
 */
public class DataSourceUtil {
	
	private static final String URL_PREFIX = "jdbc:mysql://localhost:3306/";
	
	private static final String USER_NAME = "root";
	
	private static final String PASSWORD = "root";
	
	public static DataSource createDataSource(final String dataSourceName) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		dataSource.setUrl(URL_PREFIX + dataSourceName);
		dataSource.setUsername(USER_NAME);
		dataSource.setPassword(PASSWORD);
		return dataSource;
	}
}
