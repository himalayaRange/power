package com.bly.common.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *  
 *  Redission的宗旨是促进使用者对redis的分离，让使用者将跟多的精力集中放在业务处理上
 *  [特点:
 *  	1.功能相对较简单，不支持字符串操作，不支持排序，事务和管道分区等
 *      2.可伸缩性：Jedis使用阻塞的IO,方法调用是同步的，需要等到socket处理完IO才能执行。Jedis客户端的实例是线程不安全的，所以需要通过连接池使用Jedis保证安全性
 *                Redission使用非阻塞的IO和基于Netty框架的事件驱动的通信层，方法调用是异步的。Redission的API调用是线程安全的，可以通过操作单个Redission连接完成操作。
 *      3.与第三方框架的整合
 *  ]
 *  
 *  
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月19日 下午4:40:35 
 * Created by Wang.Yi
 */
public class RedissionConfig {
	
	/**
	 * Redis Server Address
	 */
	private String address;
	
	/**
	 * Redis Server Password .Should be null is not needed
	 */
	private String password;
	
	/**
	 * Redis cluster node urls list
	 */
	private List<String> nodeAddresses = new ArrayList<String>();
	
	/**
	 * Redis slave server address
	 */
	private String masterAddress;
	
	/**
	 * Redis slave servers addresses
	 */
	private Set<String> slaveAddresses = new HashSet<String>();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getNodeAddresses() {
		return nodeAddresses;
	}

	public void setNodeAddresses(List<String> nodeAddresses) {
		this.nodeAddresses = nodeAddresses;
	}

	public String getMasterAddress() {
		return masterAddress;
	}

	public void setMasterAddress(String masterAddress) {
		this.masterAddress = masterAddress;
	}

	public Set<String> getSlaveAddresses() {
		return slaveAddresses;
	}

	public void setSlaveAddresses(Set<String> slaveAddresses) {
		this.slaveAddresses = slaveAddresses;
	}
	
	
}
