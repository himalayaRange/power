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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

/**
 * 1.范围分片算法，用于BETWEEN ，可选，如果不配置，SQL中的between and 将按照全库进行路由
 * 2.仅支持单分片
 * 
 * @author wangyi
 */
public class RangeModuloTableShardingAlgorithm implements RangeShardingAlgorithm<Long>{
	private static final Logger logger = LoggerFactory.getLogger(RangeModuloTableShardingAlgorithm.class);
	
	@Override
	public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
		logger.info("Range collection:" + JSON.toJSONString(collection) + " , rangeShardingValue:" + JSON.toJSONString(rangeShardingValue));
		Collection<String> collect = new ArrayList<>();
		Range<Long> valueRange = rangeShardingValue.getValueRange();
		for(Long i = valueRange.lowerEndpoint() ; i <= valueRange.upperEndpoint() ; i++){
			for(String each : collection){
				if(each.endsWith(i % collection.size() + "")){
					collect.add(each);
				}
			}
		}
		return collect;
	}

}
