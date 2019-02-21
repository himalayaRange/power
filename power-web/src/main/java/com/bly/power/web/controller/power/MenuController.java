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
import com.bly.power.interfaces.entity.power.MenuEntity;
import com.bly.power.interfaces.service.MenuService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Desc : 菜单管理
 * @author : Administrator
 * @date : 2017年8月24日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API + "/power/menu")
@Api(value = "/" , description = "菜单模块接口" , protocols = "http" ,hidden = true)
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	/**
	 * 获取菜单列表
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "获取菜单列表" , httpMethod = "GET"  ,  notes = "根据参数获取菜单列表" , produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(value="菜单编码" , name="menuCode" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="菜单名称" , name="menuName" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="所属系统" , name="sysId" ,dataType="Long" ,paramType="query"),
		@ApiImplicitParam(value="当前页数" , name="page" ,dataType="Integer" ,paramType="query",required=true),
		@ApiImplicitParam(value="每页显示条数" , name="rows" ,dataType="Integer" ,paramType="query",required=true)
	})
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<MenuEntity> page = menuService.selectPage(param);
			doResult(response, true, page, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增菜单信息
	 * 
	 * @param request
	 * @param response
	 * @param menuEntity
	 */
	@ApiOperation(value = "新增菜单信息" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "新增菜单")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, MenuEntity menuEntity) {
		try {
			if (menuEntity == null || StringUtils.isEmpty(menuEntity.getMenuName())
					|| StringUtils.isEmpty(menuEntity.getMenuCode())) {
				doResult(response, false, null, "编码和名称不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("menuCode", menuEntity.getMenuCode());
			param.put("menuName", menuEntity.getMenuName());
			List<MenuEntity> list = menuService.select(param);
			if (list != null && list.size() > 0) {
				doResult(response, false, null, "编码和名称已存在！");
				return;
			}
			// 保存
			menuEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			menuEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = menuService.insert(menuEntity);
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
	 * 修改菜单信息
	 * 
	 * @param request
	 * @param response
	 * @param menuEntity
	 */
	@ApiOperation(value = "更新菜单信息" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "修改菜单信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, MenuEntity menuEntity) {
		try {
			// 条件判断
			if (menuEntity == null || menuEntity.getId() == null || menuEntity.getId() <= 0) {
				doResult(response, false, null, "菜单id不能为空！");
				return;
			}
			if (menuEntity == null || StringUtils.isEmpty(menuEntity.getMenuName())
					|| StringUtils.isEmpty(menuEntity.getMenuCode())) {
				doResult(response, false, null, "名称或编码不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("menuCode", menuEntity.getMenuCode());
			param.put("menuName", menuEntity.getMenuName());
			List<MenuEntity> list = menuService.select(param);
			if (list != null && list.size() > 0) {
				for (MenuEntity role : list) {
					if (role.getId() != menuEntity.getId()) {
						doResult(response, false, null, "该编码和名称已存在！");
						return;
					}
				}
			}
			// 更新
			menuEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = menuService.update(menuEntity);
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

	// 删除某个菜单的信息
	@ApiOperation(value = "删除菜单信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结菜单信息")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="菜单的ID",required=true)@RequestParam(value = "id", required = true) Long id ){
		try {
			if(id==null){
				doResult(response, false, null, "未获取到菜单ID");
			}
			int temp = menuService.deleteById(id);
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

	// 启用、冻结菜单
	@ApiOperation(value = "冻结启用菜单" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用菜单")
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="菜单的ID",required=true)@RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true)@RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = menuService.enableOrFreeze(id, status);
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
	
	// 根据ID查询菜单信息
	@ApiOperation(value = "查看菜单" ,httpMethod = "GET"  , notes = "根据ID查看菜单")
	@RequestMapping(value = "/selectById")
	@ResponseBody
	public void selectById(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="平台系统ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入菜单ID");
			}else{
				MenuEntity model = menuService.find(id);
				doResult(response, true, model, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
