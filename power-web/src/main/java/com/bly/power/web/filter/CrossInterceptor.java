package com.bly.power.web.filter;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.system.PowerUser;
import com.bly.power.web.constant.WebConstant;

public class CrossInterceptor extends BaseController implements HandlerInterceptor {
	private List<String> excludedUrls;

	public List<String> getExcludedUrls() {
		return excludedUrls;
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	// 请求做跨域处理
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean _isLogin = false;
		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许所有域名来源
		response.addHeader("Access-Control-Allow-Methods", "*");//允许所求请求方式
		response.addHeader("Access-Control-Max-Age", "1800"); //最大的有效时间
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.addHeader("Access-Control-Allow-Credentials", "false");//是否允许发送Cookie,默认false,true表示Cookie可以包含在请求中一起发送给服务器
		//String url = request.getRequestURL().toString();
		//String uri = request.getRequestURI();
		Object redisUser = request.getSession().getAttribute(WebConstant.POWER_USER_SESSION);
		if (redisUser == null) {
			_isLogin = false;
			//doPassort(response, false, null, "无权限访问，请先登录！" + "URL:" + url + "==== URI:" + uri);
			doPassort(response, false, null, "无权限访问，请先登录！");
		} else {
			try {
				PowerUser bean =  (PowerUser)redisUser;
				Integer status = bean.getStatus();
				int isDeleted = bean.getIsDeleted();
				if(status == 1){
					doPassort(response, false, null, "您当前账户已经被冻结，请联系网站系统管理员再试...");
					_isLogin = false;
				}else if(isDeleted == 1){
					doPassort(response, false, null, "您当前账户已经被删除，如需登录请联系网站系统管理员...");
					_isLogin = false;
				}else {
					_isLogin = true;
				}
			} catch (Exception e) {
				doPassort(response, false, null, "哎呀！系统出小差了，请稍后再试...");
				_isLogin = false;
			}
		}
		
		return _isLogin;
	}

	// 分布式session拦截器
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	// 清理资源
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
