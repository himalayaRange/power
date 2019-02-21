package com.bly.common.redis.serialize;

import org.nustaq.serialization.FSTConfiguration;
import com.bly.common.redis.serialize.facade.Serializer;
/**
 * 
 * ========================================================
 * 日 期：@2017-11-8
 * 作 者：wangyi
 * 版 本：1.0.0
 * 类说明：FST-serialization在速度大小和兼容性方面重新实现了java的快速序列化包，序列化速度更快（4-10倍）、体积更小，而且兼容 JDK 原生的序列化。要求 JDK 1.7 支持。
 * 缺点：就目前而言，还不是特别的稳定，尤其是在NIO的反序列化中
 * TODO
 * ========================================================
 * 修订日期 :   
 * 修订人 :
 * 描述:
 */
public class FSTSerializer implements Serializer{

	private static FSTConfiguration config = FSTConfiguration.createDefaultConfiguration();
	//private static FSTConfiguration config = FSTConfiguration.createStructConfiguration();
	
	@Override
	public <T> byte[] getBytes(T obj) throws Exception {
		if(obj==null){
			return null;
		}
		return config.asByteArray(obj);
		
	  	/*ByteArrayOutputStream byteArrayOutputStream = null;
        FSTObjectOutput out = null;
        try {
            // stream closed in the finally
            byteArrayOutputStream = new ByteArrayOutputStream(512);
            out = new FSTObjectOutput(byteArrayOutputStream);  //32000  buffer size
            out.writeObject(obj);
            out.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {
            throw new JRedisCacheException(ex);
        } finally {
            try {
                obj = null;
                if (out != null) {
                    out.close();    //call flush byte buffer
                    out = null;
                }
                if (byteArrayOutputStream != null) {

                    byteArrayOutputStream.close();
                    byteArrayOutputStream = null;
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }*/
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getObject(byte[] objectData,Class<T> clazz) throws Exception {
		if(objectData==null){
			return null;
		}
		return (T)config.asObject(objectData);
		
		/*ByteArrayInputStream byteArrayInputStream = null;
        FSTObjectInput in = null;
        try {
            // stream closed in the finally
            byteArrayInputStream = new ByteArrayInputStream(objectData);
            in = new FSTObjectInput(byteArrayInputStream);
            return (T) in.readObject();
        } catch (ClassNotFoundException ex) {
            throw new JRedisCacheException(ex);
        } catch (IOException ex) {
            throw new JRedisCacheException(ex);
        } finally {
            try {
                objectData = null;
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                    byteArrayInputStream = null;
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }*/
	}

}
