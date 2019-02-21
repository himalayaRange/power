package com.bly.ext.rdb.sharding.jdbc.util;

import io.shardingjdbc.core.api.HintManager;

/**
 *  强制路由Util
 *
 * @author wangyi
 */
public class HintUtil {
	
	/**
	 * 添加数据源分片键
	 * @param logicTable
	 * @param shardingColumn
	 * @param value
	 */
	public static void addDatabaseShardingValue(final String logicTable , final String shardingColumn , Comparable<?> value){
		HintManager hintManager = HintManager.getInstance();
		hintManager.addDatabaseShardingValue(logicTable, shardingColumn, value);
	}
	
	/**
	 * 添加表分片键
	 * @param logicTable
	 * @param shardingColumn
	 * @param value
	 */
	public static void addTableShardingValue(final String logicTable , final String shardingColumn , Comparable<?> value){
		HintManager hintManager = HintManager.getInstance();
		hintManager.addTableShardingValue(logicTable, shardingColumn, value);
	}
	
}
