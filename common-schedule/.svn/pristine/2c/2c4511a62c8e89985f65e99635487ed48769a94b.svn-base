package com.bly.common.schedule.elastic.simple;

import com.bly.common.schedule.elastic.job.MyElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class JobDemo {
	public static void main(String[] args) {
		new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
	}
	
	private static CoordinatorRegistryCenter createRegistryCenter(){
		ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("192.168.0.60:2181", "ymy.elastic-job.demo"));
		regCenter.init();
		return regCenter;
	}
	
	private static LiteJobConfiguration createJobConfiguration(){
		 // 定义作业核心配置
	    JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob", "0/15 * * * * ?", 10).build();
	    // 定义SIMPLE类型配置
	    SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MyElasticJob.class.getCanonicalName());
	    // 定义Lite作业根配置
	    LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
	    return simpleJobRootConfig;
	}
}
