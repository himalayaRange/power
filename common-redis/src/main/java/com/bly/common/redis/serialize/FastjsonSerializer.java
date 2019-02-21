package com.bly.common.redis.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bly.common.redis.serialize.facade.Serializer;
/**
 * 
 * ========================================================
 * 日 期：2017年11月8日
 * 作 者：wangyi
 * 版 本：1.0.0
 * 类说明： Alibaba FastJson 序列化和反序列化
 * TODO
 * ========================================================
 * 修订日期 :   
 * 修订人 :
 * 描述:
 */
public class FastjsonSerializer implements Serializer{

	@Override
	public <T> byte[] getBytes(T obj) throws Exception {
		String jsonString = JSON.toJSONString(obj);
		return jsonString.getBytes("UTF-8");
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getObject(byte[] bytes,Class<T> clazz) throws Exception {
		String jsonString = new String(bytes,"UTF-8");
		Object[]  ret  = JSON.parseObject(jsonString, new TypeReference<Object[]>(){});
		//List<JSONObject> list = JSON.parseObject(jsonString, new TypeReference<ArrayList<JSONObject>>(){}); 
		return (T)ret;
	}

}
