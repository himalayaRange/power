package com.bly.common.jwt.filter;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.bly.common.jwt.annotation.ContentSecurity;

public class ContentSecurityInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isPass = true;
		
		// 解析RequestMethod
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		ContentSecurity security = method.getAnnotation(ContentSecurity.class);
		
		// 对存在注解的方法进行处理
		if(security != null) {
			switch (security.away()) {
			case DES:
				isPass = checkDES(request,response);
				break;
			}
		}
		
		return isPass;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	boolean checkDES(HttpServletRequest request , HttpServletResponse response) {
		
		// TODO
		return true;
	}
}
