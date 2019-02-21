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
package com.bly.ext.rdb.sharding.jdbc.namespace.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import io.shardingjdbc.core.api.algorithm.sharding.ListShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

/**
 * 1.复合分片策略，提供SQL中IN 、 = 、 BETWEEN 的分片支持 2.支持多分片键
 *
 * @author wangyi
 */
public class ComplexModuloTableShardingAlgorithm implements ComplexKeysShardingAlgorithm {

	@Override
	public Collection<String> doSharding(Collection<String> collection,
			Collection<ShardingValue> shardingValues) {
		 Collection<Long> orderIdValues = getShardingValue(shardingValues, "order_id");
	        Collection<Long> userIdValues = getShardingValue(shardingValues, "user_id");
	        List<String> shardingSuffix = new ArrayList<>();
	        /**例如：根据user_id + order_id 双分片键来进行分表*/
	        //Set<List<Integer>> valueResult = Sets.cartesianProduct(userIdValues, orderIdValues);
	        for (Long userIdVal : userIdValues) {
	            for (Long orderIdVal : orderIdValues) {
	                String suffix = userIdVal % 2 + "_" + orderIdVal % 2;
	                for(String x : collection){
	                	if (x.endsWith(suffix)) {
	                        shardingSuffix.add(x);
	                    }
	                }
	            }
	        }

	        return shardingSuffix;
	}

	private Collection<Long> getShardingValue(Collection<ShardingValue> shardingValues, final String key) {
		Collection<Long> valueSet = new ArrayList<>();
		Iterator<ShardingValue> iterator = shardingValues.iterator();
		while (iterator.hasNext()) {
			ShardingValue next = iterator.next();
			if (next instanceof ListShardingValue) {
				ListShardingValue value = (ListShardingValue) next;
				/** 例如：根据user_id + order_id 双分片键来进行分表 */
				if (value.getColumnName().equals(key)) {
					return value.getValues();
				}
			}
		}
		return valueSet;
	}
}
