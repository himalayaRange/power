package com.bly.common.jwt.filter;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * SQL注入拦截器
 * 
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月18日 上午9:56:38 
 * Created by Wang.Yi
 * 
 * <br>底层的数据库交互框架如Mybatis也是通过预编译的方式防止SQL的注入.
 * <br>先生成预编译的执行SQL,再通过#赋值，而$是直接参与预编译，一般用于动态表名和列明，需要手动处理防止SQL注入
 */
public class SqlInjectInterceptor implements HandlerInterceptor{

	@Override
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			// 消毒策略
			for(String value : values) {
				value  = clearXss(value);
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	/**
	 * 字符串转义处理
	 * @param value
	 * @return
	 */
	private String clearXss(String value) {
		if(value == null && "".equals(value)) {
			return value;
		}
		value = value.replaceAll("<", "<").replaceAll(">", ">");
        value = value.replaceAll("\\(", "(").replace("\\)", ")");
        value = value.replaceAll("'", "'");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
        value = value.replace("script", "");
        
        return value;
	}
}
