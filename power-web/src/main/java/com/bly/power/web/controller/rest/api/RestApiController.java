package com.bly.power.web.controller.rest.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bly.common.jwt.bean.AccessToken;
import com.bly.common.jwt.helper.JwtUtils;
import com.bly.common.jwt.security.JWTConstant;
import com.bly.common.support.cache.SpringDataRedisHelper;
import com.bly.common.utils.IPResolve;
import com.bly.common.utils.LocalNetworkIPResolve;
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.system.UserEntity;
import com.bly.power.interfaces.service.RestService;
import com.bly.power.interfaces.service.UserService;
import com.bly.power.web.constant.WebConstant;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
@Api(value = "/", description = "远程权限服务接口")
@RequestMapping(WebConstant.PREFIX_API + "/rest/api")
public class RestApiController extends BaseController {
	@Autowired
	private RestService restService;

	@Autowired
	private UserService userService;

	@Autowired
	private SpringDataRedisHelper redisService;
	
	@ApiOperation(value = "子系统用户登录", httpMethod = "POST", notes = "使用JSON WEB TOKEN 控制访问权限", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public void login(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, value = "授权用户名") @RequestParam(value = "userName", name = "userName", required = true) String userName,
			@ApiParam(required = true, value = "授权密码") @RequestParam(value = "password", name = "password", required = true) String password)
			throws Exception {
		IPResolve resolve = new LocalNetworkIPResolve();
		logger.info("start login ... remote ip :" + resolve.getServerIp());
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			doResult(response, false, null, "用户名和密码不能为空！");
			return;
		}
		UserEntity user = userService.getUserByUserName(userName);
		if (user != null) {
			if (!password.equals(user.getPassword())) { // 密码错误
				doResult(response, false, null, "密码错误！");
				return;
			}
		} else {
			doResult(response, false, null, "用户不存在！");
			return;
		}
		user.setPassword("");
		// JWT生成token
		AccessToken accessToken = new AccessToken();
		accessToken.setTokenType("bearer");
		accessToken.setUserId(String.valueOf(user.getId()));
		accessToken.setUserName(user.getUserName());
		accessToken.setRoleId(String.valueOf(user.getRoleId()));
		accessToken.setRoleName(user.getRoleName());
		accessToken.setAudience("W~sunshine~Y");
		accessToken.setExpire(7200 * 1000); // 默认access token 时间是2小时，通过定时去refeshtoken
		accessToken.setIssuser("xxxx@163.com");
		accessToken.setPrivateKey(JWTConstant.SECURITY_KEY);
		Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
		attributes.put("attributes", user);
		accessToken.setAttributes(attributes);
		String token = JwtUtils.createJWT(accessToken);
		Map<String, Object> responseData = new ConcurrentHashMap<String, Object>();
		responseData.put("userId", user.getId());
		responseData.put("accessToken", token);
		doResult(response, true, responseData, "登录成功");
	}

	@RequestMapping("/getUserPermissions")
	@ApiOperation(value = "查看用户角色权限", httpMethod = "GET", notes = "通过系统编码和角色编码查看角色所有权限" )
	@ApiImplicitParams({
		@ApiImplicitParam(value="系统编码" ,name="sysCode" ,dataType="String",paramType="query"),
		@ApiImplicitParam(value="角色编码" ,name="roleCode" ,dataType="String",paramType="query"),
	})
	public  void getCustomerPermissions(HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(name="accessToken" , required = true )String accessToken,
			@ApiParam(required = true, value = "授权用户ID") @RequestParam(value = "userId", name = "userId", required = true) String userId) {
		try {
			Map<String, Object> reqMap = Maps.newHashMap();
			String sysCode = request.getParameter("sysCode");
			String roleCode = request.getParameter("roleCode");
			if(StringUtils.isEmpty(sysCode)){
				doResult(response, false, null, "缺少参数：sysCode！");
				return;
			}
			if(StringUtils.isEmpty(roleCode)){
				doResult(response, false, null, "缺少参数：roleCode！");
				return;
			}
			reqMap.put("sysCode", sysCode);
			reqMap.put("roleCode", roleCode);
			// 从缓存中获取缓存
			String sysRoleKey  =  WebConstant.PREFIX_POWER_KEY + roleCode + "_" + sysCode;
			Serializable result = redisService.leftPopList(sysRoleKey);
			if(result == null || "".equals(result)){
				// 从数据库获取
				List<Serializable> list = restService.getSystemMenuPermissions(reqMap);
				if(list == null || (list != null && list.size() == 0)){
					doResult(response, false, null, "未获取到信息，请检查请求参数，获取当前账户未分配权限");
					return;
				}
				// 放入缓存
				redisService.leftPushList(sysRoleKey, list);
				doResult(response, true, list, "查询成功&res=d");
			}else{
				doResult(response, true, result, "查询成功$res=c");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
