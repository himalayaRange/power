package com.bly.common.jwt.filter;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.bly.common.jwt.helper.JwtUtils;
import com.bly.common.jwt.security.JWTConstant;
import com.bly.common.utils.AjaxUtils;
import com.bly.common.utils.Result;
import io.jsonwebtoken.Claims;

public class AccessTokenInterceptor implements HandlerInterceptor {
	
	// JSON WEB TOKEN 验证API访问权限
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isAllowed = false; 
		String token = request.getHeader("accessToken");
		if(token != null){
			Claims claims = JwtUtils.parseJWT(token, JWTConstant.SECURITY_KEY);
			if(claims != null) {
				Object tokenType = claims.get("type");
				String userId = request.getParameter("userId");
				if(tokenType != null && userId != null) {
					JSONObject subject = JSONObject.parseObject(claims.getSubject());
					String accessUserId = subject.getString("userId");
					if(Objects.equals("bearer", (String)tokenType) && Objects.equals(userId, accessUserId)){
						isAllowed = true;
					}else{
						AjaxUtils.renderJson(response, Result.resultPassport(null, "拒绝请求，参数伪造!"));
						isAllowed = false;
					}
				}else{
					AjaxUtils.renderJson(response, Result.resultPassport(null, "拒绝请求，参数伪造!"));
					isAllowed = false;
				}
			}else {
				AjaxUtils.renderJson(response, Result.resultPassport(null, "无效token或请求失效，请重新登录!"));
				isAllowed = false;
			}
			return isAllowed;
		}else{
			AjaxUtils.renderJson(response, Result.resultPassport(null, "拒绝请求，未获取到Token!"));
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
}
