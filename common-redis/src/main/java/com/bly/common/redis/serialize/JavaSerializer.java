package com.bly.common.redis.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.bly.common.redis.serialize.facade.Serializer;

/**
 * 
 * ========================================================
 * 日 期：@2016-12-12
 * 作 者：wangyi
 * 版 本：1.0.0
 * 类说明：Java序列化
 * TODO
 * ========================================================
 * 修订日期 :   
 * 修订人 :
 * 描述:
 */
public class JavaSerializer implements Serializer {

	
	@Override
	public <T> byte[] getBytes(T obj) throws Exception {
		if(obj==null){
			return null;
		}
		ObjectOutputStream out=null;
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			out=new ObjectOutputStream(bout);
			out.writeObject(obj);
			out.flush();
			return bout.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(out != null){
				out.close();
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getObject(byte[] bytes,Class<T> clazz) throws Exception {
		if (bytes == null) {
			return null;
		}
		ObjectInputStream oi = null;
		try {
			oi = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return (T) oi.readObject();
		} finally {
			if(oi != null){
				oi.close();
			}
		}
	}


}
