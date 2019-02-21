package com.bly.common.schedule.elastic.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
/**
 * 作业开发
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年3月13日 下午2:58:48 
 * Created by Wang.Yi
 */
public class MyElasticJob implements SimpleJob{

	@Override
	public void execute(ShardingContext context) {
		switch (context.getShardingItem()) {
		   case 0 :
			   // do something by shareing item 0
			   System.out.println("---------0----------");
		   case 1 :
			   System.out.println("---------1----------");
		   case 2 : 
			   System.out.println("---------2----------");
		}
    }
}
