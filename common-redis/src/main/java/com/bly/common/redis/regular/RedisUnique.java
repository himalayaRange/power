package com.bly.common.redis.regular;

import com.bly.common.utils.SnowflakeIdWorker;

/**
 * 通过用户自定义唯一的key,key不需要通过数据库等其他第三方存储路径进行存储
 * 
 * <p>项目名.模块.定义名称</p>
 * @author WangYi
 *
 * @date   2017年7月21日
 */
public class RedisUnique {

	/**
	 * 按项目名+模块+名称确定唯一键
	 * @param projectName
	 * @param modulesName
	 * @param defiendName
	 * @return
	 */
	public static String getUniqueKey(String projectName, String modulesName , String defiendName){
		
		return new StringBuffer().append(projectName).append(".").append(modulesName).append(".").append(defiendName).toString();
	}
	
	/**
	 * 分布式系统下生成全球唯一的ID（采用Twitter的Snowflake实现）
	 * @param workerId
	 * @param datacenterId
	 * @return
	 */
	public static long getSnowflakeIdWorker(long workerId , long datacenterId){
		
		return SnowflakeIdWorker.getSnowflakeIdWorker(workerId, datacenterId);
	}
}
