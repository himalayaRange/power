package com.bly.power.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bly.common.utils.PageParamUtil;
import com.bly.common.utils.PageUtils;
import com.bly.common.utils.SecurityUtils;
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.system.PowerUser;
import com.bly.power.interfaces.service.PowerUserService;
import com.bly.power.web.constant.WebConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(WebConstant.PREFIX_API + "/power/console/user")
@Api(value = "/" , description = "权限系统管理员模块接口" , protocols = "http" ,hidden = true)
public class PowerUserController extends BaseController {

	@Autowired
	private PowerUserService powerUserService;


	/**
	 * 获取管理员用户列表
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "查询管理员列表" , httpMethod = "GET"  ,  notes = "根据参数查询管理员" , produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(value="管理员编码" , name="userCode" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="管理员姓名" , name="realName" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="手机号" , name="phone" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="状态" , name="status" ,dataType="Integer" ,paramType="query"),
		@ApiImplicitParam(value="当前页数" , name="page" ,dataType="Integer" ,paramType="query",required=true),
		@ApiImplicitParam(value="每页显示条数" , name="rows" ,dataType="Integer" ,paramType="query",required=true)
	})
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<PowerUser> page = powerUserService.selectPage(param);
			doResult(response, true, page, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增管理员信息
	 * @param request
	 * @param response
	 * @param userEntity
	 */
	@ApiOperation(value = "新增管理员用户" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "新增管理员用户")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, PowerUser userEntity) {
		try {
			if (userEntity == null || StringUtils.isEmpty(userEntity.getUserName())
					|| StringUtils.isEmpty(userEntity.getUserCode())) {
				doResult(response, false, null, "用户名或编码不能为空！");
				return;
			}
			// 校验编码及用户名
			Map<String, Object> param = new HashMap<>();
			param.put("userCode", userEntity.getUserCode());
			List<PowerUser> list = powerUserService.select(param);
			if (list != null && list.size() > 0) {
				doResult(response, false, null, "编码已经存在！");
				return;
			}
			param.clear();
			param.put("userName", userEntity.getUserName());
			list = powerUserService.select(param);
			if (list != null && list.size() > 0) {
				doResult(response, false, null, "用户名已经存在！");
				return;
			}
			// 保存
			userEntity.setPassWord(SecurityUtils.encryptMD5(userEntity.getPassWord()));
			int temp = powerUserService.insert(userEntity);
			if (temp > 0) {
				doResult(response, true, null, "新增成功");
			} else {
				doResult(response, false, null, "新增失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 修改管理员信息
	 * 
	 * @param request
	 * @param response
	 * @param userEntity
	 */
	@ApiOperation(value = "更新管理员信息" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "修改管理员信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, PowerUser userEntity) {
		try {
			// 条件判断
			if (userEntity == null || userEntity.getId() == null || userEntity.getId() <= 0) {
				doResult(response, false, null, "用户id不能为空！");
				return;
			}
			if (userEntity == null || StringUtils.isEmpty(userEntity.getUserName())
					|| StringUtils.isEmpty(userEntity.getUserCode())) {
				doResult(response, false, null, "用户名或编码不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("userCode", userEntity.getUserCode());
			param.put("userName", userEntity.getUserName());
			List<PowerUser> list = powerUserService.select(param);
			if (list != null && list.size() > 0) {
				for (PowerUser role : list) {
					if (role.getId() != userEntity.getId()) {
						doResult(response, false, null, "该编码和名称已存在！");
						return;
					}
				}
			}
			// 更新
			userEntity.setPassWord(SecurityUtils.encryptMD5(userEntity.getPassWord()));
			int temp = powerUserService.update(userEntity);
			if (temp > 0) {
				PowerUser ue = powerUserService.getUserByUserName(userEntity.getUserName());
				ue.setPassWord("");
				request.getSession().setAttribute(WebConstant.POWER_USER_SESSION, ue); //更新session
				doResult(response, true, null, "修改成功");
			} else {
				doResult(response, false, null, "修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	// 删除某个用户的信息
	@ApiOperation(value = "删除管理员信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结用户信息")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="用户的ID",required=true)@RequestParam(value = "id", required = true) Long id ){
		try {
			int temp = powerUserService.deleteById(id);
			if (temp > 0) {
				PowerUser ue = powerUserService.find(id);
				ue.setPassWord("");
				request.getSession().setAttribute(WebConstant.POWER_USER_SESSION, ue);
				doResult(response, true, null, "删除成功");
			} else {
				doResult(response, false, null, "删除失败,只有冻结的才能删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	// 启用、冻结用户
	@ApiOperation(value = "冻结启用管理员" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用管理员")
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="公司的ID",required=true)@RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true)@RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = powerUserService.enableOrFreeze(id, status);
			if (temp > 0) {
				PowerUser ue = powerUserService.find(id);
				ue.setPassWord("");
				request.getSession().setAttribute(WebConstant.POWER_USER_SESSION, ue);
				doResult(response, true, null, "操作成功");
			} else {
				doResult(response, false, null, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	
	@ApiOperation(value="查看管员" , httpMethod="GET" , produces="application/json;charset=UTF-8" , notes="根据ID查询用户")
	@ResponseBody
	@RequestMapping(value="/selectById" , method=RequestMethod.GET)
	public void selectById(HttpServletRequest request , HttpServletResponse response,
			@ApiParam(name="id",value="用户ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入用户ID");
			}else{
				PowerUser model = powerUserService.find(id);
				doResult(response, true, model, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
}
