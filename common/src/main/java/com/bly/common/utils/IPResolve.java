package com.bly.common.utils;

/**
 * 
 * ========================================================
 * 日 期：@2016-11-23
 * 作 者：wangyi
 * 版 本：1.0.0
 * 1）可以从物理机器或者虚拟机器的特殊文件中获取服务
 * 2）可以获取指定网卡的IP
 * TODO
 * ========================================================
 * 修订日期 :   
 * 修订人 :
 * 描述:
 */
public interface IPResolve {

	public String getServerIp() throws Exception;
	
	public void reset();
	
	//IP发生改变的时候看，进行回调
	public static interface IpResetCallBack{
		
		public void rest(String newIp);
	}
}
