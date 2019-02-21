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
package com.bly.ext.rdb.sharding.jdbc.java.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import io.shardingjdbc.core.api.HintManager;

/**
 * INIT DATA REPOSITORY
 *
 * @author wangyi
 */
public class RawJdbcRepository {

	private final DataSource dataSource;

	public RawJdbcRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void initData() throws SQLException {
        createTable();
        insertData();
    }
	
	public void queryPrintEqualsSelect() throws SQLException{
		System.out.println("1.Equals Select--------------");
	    printEqualsSelect();
	}
	
	public void queryPrintInSelect() throws SQLException{
		System.out.println("2.In Select--------------");
        printInSelect();
	}
	
	public void queryPrintHintSimpleSelect() throws SQLException{
		 System.out.println("3.Hint Select--------------");
	     printHintSimpleSelect();
	}
	
	public void destoryTable() throws SQLException{
		dropTable();
	}

	public void createTable() throws SQLException {
		execute(dataSource,
				"CREATE TABLE IF NOT EXISTS t_order (order_id BIGINT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_id))");
		execute(dataSource,
				"CREATE TABLE IF NOT EXISTS t_order_item (order_item_id BIGINT NOT NULL AUTO_INCREMENT, order_id BIGINT NOT NULL, user_id INT NOT NULL, PRIMARY KEY (order_item_id))");
	}

	public void dropTable() throws SQLException {
		execute(dataSource, "DROP TABLE t_order_item");
		execute(dataSource, "DROP TABLE t_order");
	}

	public void insertData() throws SQLException {
		for (int i = 1; i < 10; i++) {
			long orderId = executeAndGetGeneratedKey(dataSource,
					"INSERT INTO t_order (user_id, status) VALUES (10, 'INIT')");
			execute(dataSource, String.format("INSERT INTO t_order_item (order_id, user_id) VALUES (%d, 10)", orderId));
			orderId = executeAndGetGeneratedKey(dataSource,
					"INSERT INTO t_order (user_id, status) VALUES (11, 'INIT')");
			execute(dataSource, String.format("INSERT INTO t_order_item (order_id, user_id) VALUES (%d, 11)", orderId));
		}
	}

	public void printEqualsSelect() throws SQLException {
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=?";
		try (Connection conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, 10);
			printSimpleSelect(preparedStatement);
		}
	}

	public void printInSelect() throws SQLException {
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id IN (?, ?)";
		try (Connection conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, 10);
			preparedStatement.setInt(2, 11);
			printSimpleSelect(preparedStatement);
		}
	}

	public void printHintSimpleSelect() throws SQLException {
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id";
		HintManager hintManager = null ;
		try {
		    hintManager = HintManager.getInstance();
			Connection conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			hintManager.addDatabaseShardingValue("t_order", "user_id", 11); 
			printSimpleSelect(preparedStatement);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			hintManager.close();
		}
	}

	private void printSimpleSelect(final PreparedStatement preparedStatement) throws SQLException {
		try (ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				System.out.print("order_item_id:" + rs.getLong(1) + ", ");
				System.out.print("order_id:" + rs.getLong(2) + ", ");
				System.out.print("user_id:" + rs.getInt(3));
				System.out.println();
			}
		}
	}

	private void execute(final DataSource dataSource, final String sql) throws SQLException {
		try (Connection conn = dataSource.getConnection(); Statement statement = conn.createStatement()) {
			statement.execute(sql);
		}
	}

	private long executeAndGetGeneratedKey(final DataSource dataSource, final String sql) throws SQLException {
		long result = -1;
		try (Connection conn = dataSource.getConnection(); Statement statement = conn.createStatement()) {
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				result = resultSet.getLong(1);
			}
		}
		return result;
	}
}
