package com.bly.common.redis.serialize.facade;
/**
 * 
 * ========================================================
 * 日 期：@2016-12-12
 * 作 者：wangyi
 * 版 本：1.0.0
 * 类说明：序列化接口<此处序列化采用而二进制流格式进行传输>
 * TODO
 * ========================================================
 * 修订日期 :   
 * 修订人 :
 * 描述:
 */
public interface Serializer {

	public <T>  byte[] getBytes(T obj)throws Exception;
	
	public <T> T getObject(byte[] bytes,Class<T> cls)throws Exception;
	
}
