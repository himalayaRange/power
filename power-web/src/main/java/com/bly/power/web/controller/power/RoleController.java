package com.bly.power.web.controller.power;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import com.alibaba.fastjson.JSON;
import com.bly.common.utils.PageParamUtil;
import com.bly.common.utils.PageUtils;
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.power.RoleEntity;
import com.bly.power.interfaces.entity.power.vo.SystemMenuVo;
import com.bly.power.interfaces.entity.rest.MenuColumnRoot;
import com.bly.power.interfaces.entity.rest.MenuRoot;
import com.bly.power.interfaces.entity.rest.RoleMenuRoot;
import com.bly.power.interfaces.service.RestService;
import com.bly.power.interfaces.service.RoleService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Desc : 角色管理
 * @author : Administrator
 * @date : 2017年8月23日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API + "/power/role")
@Api(value = "/" , description = "角色管理接口" , protocols = "http" ,hidden = true)
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private RestService restService;
	/**
	 * 获取角色列表
	 * @param request
	 * @param response
	 */
	@ApiOperation(value="查询角色列表" , httpMethod="GET" , notes="根据条件查询角色" , produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(value="角色编码" , name="roleCode" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="角色名称" , name="roleName" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="当前页数" , name="page" ,dataType="Integer" ,paramType="query",required=true),
		@ApiImplicitParam(value="每页显示条数" , name="rows" ,dataType="Integer" ,paramType="query",required=true)
	})
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			Object  timeZones = param.get("createTime");
			if(timeZones != null && (! ",".equals(timeZones))){
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z" , Locale.ENGLISH);
				String timeZonesStr = (String) timeZones;
				String[] times = timeZonesStr.split(",");
				String startTime = times[0];
				String endTime = times[1];
				param.put("startTime", sdf.parse(startTime.replaceAll("GMT", "")));
				param.put("endTime", sdf.parse(endTime.replaceAll("GMT", "")));
			}
			PageUtils<RoleEntity> page = roleService.selectPage(param);
			doResult(response, true, page, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	/**
	 * 初始化所有角色列表
	 * @param request
	 * @param response
	 */
	@ApiOperation(value="初始化所有角色列表" , httpMethod="GET" , notes="初始化所有角色列表" , produces = "application/json;charset=UTF-8")
	@RequestMapping(value = "/initTotalRoles", method = RequestMethod.GET)
	@ResponseBody
	public void initTotalRoles(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Map<String,Object>> list = roleService.initTotalRoles();
			doResult(response, true, list, "查询成功！");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	/**
	 * 新增角色信息
	 * 
	 * @param request
	 * @param response
	 * @param roleEntity
	 */
	@ApiOperation(value="新增角色信息",httpMethod="POST" , produces = "application/json;charset=UTF-8" , notes = "新增角色信息")
	@RequestMapping(value = "/insert", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public synchronized void insert(HttpServletRequest request, HttpServletResponse response, 
			RoleEntity roleEntity // , @RequestBody List<SystemMenuVo> smvs
			){
		try {
			String smvstring = request.getParameter("smvs");
			String result = smvstring.replaceAll("\\＂", "\\'");
			List<SystemMenuVo> smvs = JSON.parseArray(result, SystemMenuVo.class);
			if (roleEntity == null || StringUtils.isEmpty(roleEntity.getRoleName())
					|| StringUtils.isEmpty(roleEntity.getRoleCode())) {
				doResult(response, false, null, "编码和名称不能为空！");
				return;
			}
			// 检验编码和名称是否重复
			Map<String, Object> param = new HashMap<>();
			param.put("roleCode", roleEntity.getRoleCode());
			param.put("roleName", roleEntity.getRoleName());
			List<RoleEntity> list = roleService.select(param);
			if (list != null && list.size() > 0) {
				doResult(response, false, null, "编码和名称已存在！");
				return;
			}
			// 保存、
			roleEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			roleEntity.setCreateTime(new Date());
			roleEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			try {
				for(int i = 0 ; i < smvs.size() ; i++){
					List<SystemMenuVo> sm = Lists.newArrayList();
					sm.add(smvs.get(i));
					roleService.insertRoleInfo(roleEntity, sm);
				}
				doResult(response, true, null, "新增成功");
			} catch (Exception e) {
				doResult(response, false, null, "新增失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 修改角色信息
	 * 
	 * @param request
	 * @param response
	 * @param roleEntity
	 */
	@ApiOperation(value="修改角色信息",httpMethod="POST" , produces = "application/json;charset=UTF-8" , notes = "修改角色信息")
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public synchronized void update(HttpServletRequest request, HttpServletResponse response, RoleEntity roleEntity
			//,@RequestBody List<SystemMenuVo> smvs
			) {
		try {
			String smvstring = request.getParameter("smvs");
			String result = smvstring.replaceAll("\\＂", "\\'");
			List<SystemMenuVo> smvs = JSON.parseArray(result, SystemMenuVo.class);
			// 条件判断
			if (roleEntity == null || roleEntity.getId() == null || roleEntity.getId() <= 0) {
				doResult(response, false, null, "角色id不能为空！");
				return;
			}
			if (roleEntity == null || StringUtils.isEmpty(roleEntity.getRoleName())
					|| StringUtils.isEmpty(roleEntity.getRoleCode())) {
				doResult(response, false, null, "编码和名称不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("roleCode", roleEntity.getRoleCode());
			param.put("roleName", roleEntity.getRoleName());
			//更新
			roleEntity.setUpdateTime(new Date());
			roleEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			try {
				boolean proceduceFlag = false;
				for(int i = 0 ; i < smvs.size() ; i++){
					if(i == 0 ){
						proceduceFlag = true;
					}else{
						proceduceFlag = false;
					}
					List<SystemMenuVo> sm = Lists.newArrayList();
					sm.add(smvs.get(i));
					roleService.updateRoleInfo(roleEntity , sm , proceduceFlag);
				}
				doResult(response, true, null, "修改成功");
			} catch (Exception e) {
				doResult(response, false, null, "修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	// 删除某个角色的信息
	@RequestMapping(value = "/delete")
	@ApiOperation(value = "删除角色信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结角色信息")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value="角色ID" , name = "id" , required = true)@RequestParam(value = "id", required = true) Long id) {
		try {
			int temp = roleService.deleteById(id);
			if (temp > 0) {
				doResult(response, true, null, "删除成功");
			} else {
				doResult(response, false, null, "删除失败，只有冻结的才能删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	// 启用、冻结角色
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	@ApiOperation(value = "冻结启用角色" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用角色")
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="角色ID",required=true)@RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true)@RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = roleService.enableOrFreeze(id,status);
			if (temp > 0) {
				doResult(response, true, null, "操作成功");
			} else {
				doResult(response, false, null, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	// 根据ID查询某个平台系统
	@ApiOperation(value = "查看角色信息" ,httpMethod = "GET"  , notes = "根据ID查看角色信息")
	@RequestMapping(value = "/selectById")
	@ResponseBody
	public void selectById(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="角色ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入角色ID");
			}else{
				RoleEntity model = roleService.find(id);
				doResult(response, true, model, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 初始化所有平台的所有资源列表
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/initResourcesList")
	@ApiOperation(value = "查看所有资源" ,httpMethod = "GET"  , notes = "查看所有资源")
	public void initResourcesList(HttpServletRequest request , HttpServletResponse response){
		try {
			 List<Map<String,Object>> list = roleService.initResourcesMaps();
			 doResult(response, true, list, "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	/**
	 * 按角色查询权限
	 * @param request
	 * @param response
	 * @param accessToken
	 * @param userId
	 */
	@RequestMapping("/queryPermissionsByRoleInfo")
	@ApiOperation(value = "按角色查询权限", httpMethod = "GET", notes = "按角色查询权限" )
	@ApiImplicitParams({
		@ApiImplicitParam(value="系统编码" ,name="sysCode" ,dataType="String",paramType="query"),
		@ApiImplicitParam(value="角色编码" ,name="roleCode" ,dataType="String",paramType="query"),
	})
	public void getCustomerPermissions(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqMap = new HashMap<String, Object>();
			String sysCode = request.getParameter("sysCode");
			String roleCode = request.getParameter("roleCode");
			reqMap.put("sysCode", sysCode);
			reqMap.put("roleCode", roleCode);
			List<Serializable> list = restService.getSystemMenuPermissions(reqMap);
			// 封装成前端格式
			List<Map<String,Object>> srcList = new ArrayList<Map<String,Object>>();
			for(Serializable item : list) {
				MenuRoot root = (MenuRoot)item;
				Long sysId = root.getSysId();
				List<RoleMenuRoot> roleMenus = root.getRoleMenus();
				for(RoleMenuRoot menuRoot : roleMenus){
					Long menuId = menuRoot.getMenuId();
					List<MenuColumnRoot> columns = menuRoot.getColumns();
					for(MenuColumnRoot columnRoot : columns) {
						Long columnId = columnRoot.getColumnId();
						Map<String,Object> sv = new HashMap<String,Object>();
						sv.put("sysId", sysId);
						sv.put("menuId", menuId);
						sv.put("columnId", columnId);
						srcList.add(sv);
					}
				}
			}
			doResult(response, true, srcList, "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
}
