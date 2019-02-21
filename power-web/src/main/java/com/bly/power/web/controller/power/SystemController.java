package com.bly.power.web.controller.power;

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
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.power.SystemEntity;
import com.bly.power.interfaces.service.SystemService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Desc   : 系统管理
 * @author : Administrator
 * @date   : 2017年8月24日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API +  "/power/system")
@Api(value = "/" , description = "系统模块接口" , protocols = "http" ,hidden = true)
public class SystemController extends BaseController {

	@Autowired
	private SystemService systemService;


	/**
	 * 获取系统列表
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "查询平台系统列表" , httpMethod = "GET"  ,  notes = "根据参数查询平台系统列表" , produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(value="系统编码" , name="sysCode" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="系统名称" , name="sysName" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="当前页数" , name="page" ,dataType="Integer" ,paramType="query",required=true),
		@ApiImplicitParam(value="每页显示条数" , name="rows" ,dataType="Integer" ,paramType="query",required=true)
	})
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<SystemEntity> page = systemService.selectPage(param);
			doResult(response, true, page, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增系统信息
	 * 
	 * @param request
	 * @param response
	 * @param systemEntity
	 */
	@ApiOperation(value = "新增平台系统" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "新增平台系统")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, SystemEntity systemEntity) {
		try {
			if (systemEntity == null || StringUtils.isEmpty(systemEntity.getSysName())
					|| StringUtils.isEmpty(systemEntity.getSysCode())) {
				doResult(response, false, null, "请正确填写系统信息！");
				return;
			}
			//校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("sysCode", systemEntity.getSysCode());
			param.put("sysName", systemEntity.getSysName());
			List<SystemEntity> list = systemService.select(param);
			if(list != null && list.size()>0){
				doResult(response, false, null, "编码和名称已存在！");
				return;
			}
			//保存
			systemEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			systemEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = systemService.insert(systemEntity);
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
	 * 修改系统信息
	 * 
	 * @param request
	 * @param response
	 * @param systemEntity
	 */
	@ApiOperation(value = "更新平台系统信息" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "修改平台系统信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, SystemEntity systemEntity) {
		try {
			// 条件判断
			if (systemEntity == null || systemEntity.getId() == null || systemEntity.getId() <= 0) {
				doResult(response, false, null, "系统id不能为空！");
				return;
			}
			if (systemEntity == null || StringUtils.isEmpty(systemEntity.getSysName())
					|| StringUtils.isEmpty(systemEntity.getSysCode())) {
				doResult(response, false, null, "名称或编码不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("sysCode", systemEntity.getSysCode());
			param.put("sysName", systemEntity.getSysName());
			List<SystemEntity> list = systemService.select(param);
			if (list != null && list.size() > 0) {
				for (SystemEntity role : list) {
					if (role.getId() != systemEntity.getId()) {
						doResult(response, false, null, "该编码和名称已存在！");
						return;
					}
				}
			}
			// 更新
			systemEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = systemService.update(systemEntity);
			if (temp > 0) {
				doResult(response, true, null, "修改成功");
			} else {
				doResult(response, false, null, "修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	// 删除某个系统的信息
	@ApiOperation(value = "删除平台系统信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结平台系统信息")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", required = true) Long id) {
		try {
			if(id ==  null){
				doResult(response, false, null, "未获取到平台系统ID");
			}
			int temp = systemService.deleteById(id);
			if (temp > 0) {
				doResult(response, true, null, "删除成功");
			} else {
				doResult(response, false, null, "删除失败,只有冻结的才能删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	
	// 启用、冻结平台系统
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	@ApiOperation(value = "冻结启用平台系统" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用平台系统")
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="平台系统的ID",required=true)@RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true)@RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = systemService.enableOrFreeze(id,status);
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
	@ApiOperation(value = "查看系统" ,httpMethod = "GET"  , notes = "根据ID查看系统")
	@RequestMapping(value = "/selectById")
	@ResponseBody
	public void selectById(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="平台系统ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入平台ID");
			}else{
				SystemEntity model = systemService.find(id);
				doResult(response, true, model, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
