package com.bly.power.web.controller;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bly.common.support.cache.RedissonHelper;
import com.bly.common.support.cache.SpringDataRedisHelper;
import com.bly.common.utils.IPResolve;
import com.bly.common.utils.LocalNetworkIPResolve;
import com.bly.common.utils.SecurityUtils;
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.system.CompanyEntity;
import com.bly.power.interfaces.entity.system.PowerUser;
import com.bly.power.interfaces.service.PowerUserService;
import com.bly.power.web.constant.WebConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Desc : 登录
 * @author : Administrator
 * @date : 2017年9月1日
 */
@Api(value = "/LoginController" , description = "用户登录接口" )
@RestController
@RequestMapping(WebConstant.PREFIX_API + "/")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private PowerUserService powerUserService;

	@Autowired 
	private SpringDataRedisHelper cacheThreadService;
	
	@Autowired
	private RedissonHelper redissonService;
	
	/**
	 * 系统登录
	 * 
	 * @param request
	 * @param response
	 * @param userEntity
	 * @throws Exception 
	 */
	//@ApiIgnore
	@ApiOperation(value = "系统登录" ,httpMethod = "POST" , notes = "登录入口,由servlet容器管理" , consumes="application/json" , produces="application/json")
	@RequestMapping(value="login",method = {RequestMethod.POST})
	public void login(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true ,value = "用户名")@RequestParam(value="userName",name="userName",required=true)String userName,
			@ApiParam(required = true ,value = "密码")@RequestParam(value="password",name="password",required=true)String password
			) throws Exception {
		Enumeration parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String paramName = (String)parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if(paramValues.length == 1){
				String paramValue = paramValues[0];
				System.out.println("key:" + paramName + "value:" + paramValue);
			}
		}
		//String userName = request.getParameter("userName");
		//String password = request.getParameter("password");
		IPResolve resolve =  new LocalNetworkIPResolve();
		logger.info("start login ... remote ip :" + resolve.getServerIp());
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			doResult(response, false, null, "用户名和密码不能为空！");
			return;
		}
		PowerUser user = powerUserService.getUserByUserName(userName);
		if (user != null) { // 存在用户
			
			if (!SecurityUtils.encryptMD5(password).equals(user.getPassWord())) { // 密码错误
			//if (!password.equals(user.getPassword())) { // 密码错误
				doResult(response, false, null, "密码错误");
				return;
			}
		} else {
			doResult(response, false, null, "用户名不存在");
			return;
		}
		// 保存session
		user.setPassWord(""); // 去掉密码
		request.getSession().setAttribute(WebConstant.POWER_USER_SESSION, user);
		doResult(response, true, user, "登录成功");
	}

	@ApiOperation(value="登录注销",httpMethod="GET" ,notes="退出单点登录")
	@RequestMapping("sso/logout")
	public void logout(HttpServletRequest request , HttpServletResponse response){
		request.getSession().removeAttribute(WebConstant.POWER_USER_SESSION);
		request.getSession().invalidate();
		doResult(response, true, null, "注销成功");
	}
	
	@ApiOperation(value="springData设置缓存",httpMethod="POST" ,notes="设置缓存")
	@RequestMapping("cache/spring/setCache")
	public void setRedisCache(HttpServletRequest request , HttpServletResponse response){
		CompanyEntity entity = new CompanyEntity();
		entity.setComName("佰丽源");
		entity.setAddress("杭州市下城区永波街");
		if(cacheThreadService != null) {
			cacheThreadService.set("test", "测试缓存");
			cacheThreadService.set("model", entity);
			doResult(response, true, null , "缓存设置成功");
		}else{
			doResult(response, true, null , "缓存设置失败，未获取到缓存线程池");
		}
	}
	
	@ApiOperation(value="springData获取缓存",httpMethod="GET" ,notes="获取缓存")
	@RequestMapping("cache/spring/getCache")
	public void getRedisCache(HttpServletRequest request , HttpServletResponse response){
		if(cacheThreadService != null) {
			Object object = cacheThreadService.get("test");
			Object object2 = cacheThreadService.get("model");
			String test = object == null ? null : (String) object;
			CompanyEntity model = object2 == null ? null : (CompanyEntity) object2;
			StringBuffer sb = new StringBuffer();
			sb.append("字符串：").append(test).append("</br>").append("序列化实体：").append(model == null ? "" : model.toString());
			doResult(response, true, sb.toString() , "获取缓存成功");
		}else{
			doResult(response, false, null, "缓存已超时失效");
		}
	}
	
	@ApiOperation(value="redisson设置缓存",httpMethod="POST" ,notes="设置缓存")
	@RequestMapping("cache/redisson/setCache")
	public void setRedissonCache(HttpServletRequest request , HttpServletResponse response){
		CompanyEntity entity = new CompanyEntity();
		entity.setComName("阿里巴巴");
		entity.setAddress("杭州市西湖区");
		if(redissonService != null) {
			redissonService.set("test", "redisson测试缓存");
			redissonService.set("model", entity);
			doResult(response, true, null , "缓存设置成功");
		}else{
			doResult(response, true, null , "缓存设置失败，未获取到缓存线程池");
		}
	}
	
	@ApiOperation(value="redisson获取缓存",httpMethod="GET" ,notes="获取缓存")
	@RequestMapping("cache/redisson/getCache")
	public void getRedissonCache(HttpServletRequest request , HttpServletResponse response){
		if(redissonService != null) {
			Object object = redissonService.get("test");
			Object object2 = redissonService.get("model");
			String test = object == null ? null : (String) object;
			CompanyEntity model = object2 == null ? null : (CompanyEntity) object2;
			StringBuffer sb = new StringBuffer();
			sb.append("字符串：").append(test).append("</br>").append("序列化实体：").append(model == null ? "" : model.toString());
			doResult(response, true, sb.toString() , "获取缓存成功");
		}else{
			doResult(response, false, null, "缓存已超时失效");
		}
	}
	
}
