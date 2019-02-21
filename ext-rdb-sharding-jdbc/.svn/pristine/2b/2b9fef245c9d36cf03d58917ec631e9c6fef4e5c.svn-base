/*
 * Copyright 2008-2018 blysy.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.bly.ext.rdb.sharding.jdbc.namespace.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.util.Collection;
/**
 *  采用精确定位算法分库
 *
 * @author wangyi
 */
public final class PreciseModuloDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    
    @Override
    public String doSharding(final Collection<String> collection, final PreciseShardingValue<Integer> shardingValue) {
        for (String each : collection) {
            // 按奇偶数分库
        	if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        	// 按数据均匀分布
        	// if (each.endsWith(shardingValue.getValue() % collection.size() + "")){
        	//	return each;
        	//}
        }
        throw new UnsupportedOperationException();
    }
}
