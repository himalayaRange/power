package com.bly.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * <pre>
 * 功能：httpClient访问远程接口工具类
 * 日期：2015年3月17日 上午11:19:21
 * </pre>
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

    /**
     * <pre>
     * 方法体说明：向远程接口发起请求，返回字符串类型结果
     * @param url 接口地址
     * @param requestMethod 请求类型
     * @param params 传递参数
     * @return String 返回结果
     * </pre>
     */
    public static String httpRequestToString(String url, String requestMethod,
            Map<String, String> params, Map<String ,Object> headers , String ...auth){
        //接口返回结果
        String methodResult = null;
        try {
            String parameters = "";
            boolean hasParams = false;
            //将参数集合拼接成特定格式，如name=zhangsan&age=24
            for(String key : params.keySet()){
                String value = URLEncoder.encode(params.get(key), "UTF-8");
                parameters += key +"="+ value +"&";
                hasParams = true;
            }
            if(hasParams){
                parameters = parameters.substring(0, parameters.length()-1);
            }
            //是否为GET方式请求
            boolean isGet = "get".equalsIgnoreCase(requestMethod);
            boolean isPost = "post".equalsIgnoreCase(requestMethod);
            boolean isPut = "put".equalsIgnoreCase(requestMethod);
            boolean isDelete = "delete".equalsIgnoreCase(requestMethod);

            //创建HttpClient连接对象
            DefaultHttpClient client = new DefaultHttpClient();
            HttpRequestBase method = null;
            if(isGet){
                url += "?" + parameters;
                method = new HttpGet(url);
            }else if(isPost){
                method = new HttpPost(url);
                HttpPost postMethod = (HttpPost) method;
                StringEntity entity = new StringEntity(parameters);
                postMethod.setEntity(entity);
            }else if(isPut){
                method = new HttpPut(url);
                HttpPut putMethod = (HttpPut) method;
                StringEntity entity = new StringEntity(parameters);
                putMethod.setEntity(entity);
            }else if(isDelete){
                url += "?" + parameters;
                method = new HttpDelete(url);
            }
            method.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
            //设置参数内容类型
            method.addHeader("Content-Type","application/x-www-form-urlencoded");
            //httpClient本地上下文
            HttpClientContext context = null;
            if(!(auth==null || auth.length==0 || "".equals(""))){
                String username = auth[0];
                String password = auth[1];
                UsernamePasswordCredentials credt = new UsernamePasswordCredentials(username,password);
                //凭据提供器
                CredentialsProvider provider = new BasicCredentialsProvider();
                //凭据的匹配范围
                provider.setCredentials(AuthScope.ANY, credt);
                context = HttpClientContext.create();
                context.setCredentialsProvider(provider);
            }
            if(headers != null){
            	for(Map.Entry<String, Object> entry : headers.entrySet()){
            		method.addHeader(entry.getKey(), entry.getValue() == null ? null : (String) entry.getValue());
            	}
            }
            //访问接口，返回状态码
            HttpResponse response = client.execute(method, context);
            //返回状态码200，则访问接口成功
            if(response.getStatusLine().getStatusCode()==200){
                methodResult = EntityUtils.toString(response.getEntity());
            }
            client.close();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return methodResult;
    }
}
