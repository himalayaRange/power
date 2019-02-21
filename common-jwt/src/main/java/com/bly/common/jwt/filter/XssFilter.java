package com.bly.common.jwt.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.bly.common.jwt.wrapper.XssAndSqlHttpServletRequestWrapper;

/**
 * 防SQL注入、防XSS攻击（Cross Site Script） Copyright 2018 CoderDream's Studio All right
 * reserved Created on 2018年1月18日 上午9:57:15 Created by Wang.Yi
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 对请求进行过滤，并将过滤后的参数由拦截器链传递下去
		XssAndSqlHttpServletRequestWrapper xssAndSqlHttpServletRequestWrapper = new XssAndSqlHttpServletRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(xssAndSqlHttpServletRequestWrapper, response);
	}

	@Override
	public void destroy() {

	}

}
