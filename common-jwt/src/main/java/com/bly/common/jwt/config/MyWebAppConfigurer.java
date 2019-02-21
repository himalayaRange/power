package com.bly.common.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.bly.common.jwt.filter.AccessTokenInterceptor;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 添加拦截器拦截规则
		// excludePathPatterns 用户排除
		registry.addInterceptor(new AccessTokenInterceptor()).addPathPatterns("/rest/api/jwt/**").excludePathPatterns("/rest/api/login");
		super.addInterceptors(registry);
	}

}
