package com.bly.common.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * ========================================================
 * 日 期：2017年7月22日
 * 作 者：wangyi
 * 版 本：1.0.0
 * 类说明：解析网卡IP
 * TODO
 * ========================================================
 * 修订日期 :   
 * 修订人 :
 * 描述:
 */
public class LocalNetworkIPResolve implements IPResolve{

	private static final Logger LOG = LoggerFactory.getLogger(LocalNetworkIPResolve.class);
	
	private String serverIp;
	
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	@Override
	public String getServerIp() throws Exception {
		if(serverIp!=null){
			return serverIp;
		}
		//一个主机有多个网络接口
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = netInterfaces.nextElement();
				// 每个网络接口,都会有多个"网络地址",比如一定会有lookback地址,会有siteLocal地址等.以及IPV4或者IPV6 .
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = addresses.nextElement();
					if(address instanceof Inet6Address){
						continue;
					}
					if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
						serverIp = address.getHostAddress();
						LOG.info("resolve server ip : {}", serverIp);
						continue;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return serverIp;
	}

	@Override
	public void reset() {
		serverIp=null;
	}

}
