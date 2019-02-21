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
import com.bly.power.interfaces.entity.power.MenuColumnEntity;
import com.bly.power.interfaces.service.MenuColumnService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Desc : 菜单列管理
 * @author : Administrator
 * @date : 2017年8月25日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API + "/power/menucol")
@Api(value = "/" , description = "菜单功能接口" , protocols = "http" ,hidden = true)
public class MenuColumnController extends BaseController {

	@Autowired
	private MenuColumnService menuColumnService;

	/**
	 * 获取菜单列列表
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "查询菜单功能列表" , httpMethod = "GET"  ,  notes = "根据参数查询菜单功能列表" , produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(value="所属菜单ID" ,name="menuId" ,dataType="Long",paramType="query"),
		@ApiImplicitParam(value="菜单功能编码" , name="colCode" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="菜单功能名称" , name="colName" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="当前页数" , name="page" ,dataType="Integer" ,paramType="query",required=true),
		@ApiImplicitParam(value="每页显示条数" , name="rows" ,dataType="Integer" ,paramType="query",required=true)
	})
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<MenuColumnEntity> page = menuColumnService.selectPage(param);
			doResult(response, true, page, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增菜单列信息
	 * @param request
	 * @param response
	 * @param menuColumnEntity
	 */
	@ApiOperation(value = "新增菜单功能" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "新增菜单功能")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, MenuColumnEntity menuColumnEntity) {
		try {
			if (menuColumnEntity == null || StringUtils.isEmpty(menuColumnEntity.getColName())
					|| StringUtils.isEmpty(menuColumnEntity.getColCode())) {
				doResult(response, false, null, "编码和名称不能为空！");
				return;
			}
			// 校验菜单ID和编码
			Map<String, Object> param = new HashMap<>();
			param.put("menuId", menuColumnEntity.getMenuId());
			param.put("colCode", menuColumnEntity.getColCode());
			List<MenuColumnEntity> list = menuColumnService.select(param);
			if (list != null && list.size() > 0) {
				doResult(response, false, null, "功能名称和功能编码已存在！");
				return;
			}
			
			//保存
			menuColumnEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			menuColumnEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = menuColumnService.insert(menuColumnEntity);
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
	 * 修改菜单列信息
	 * 
	 * @param request
	 * @param response
	 * @param menuColumnEntity
	 */
	@ApiOperation(value = "更新菜单功能信息" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "修改菜单功能信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, MenuColumnEntity menuColumnEntity) {
		try {
			// 条件判断
			if (menuColumnEntity == null || menuColumnEntity.getId() == null || menuColumnEntity.getId() <= 0) {
				doResult(response, false, null, "菜单列id不能为空！");
				return;
			}
			if (menuColumnEntity == null || StringUtils.isEmpty(menuColumnEntity.getColName())
					|| StringUtils.isEmpty(menuColumnEntity.getColCode())) {
				doResult(response, false, null, "名称或编码不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("colCode", menuColumnEntity.getColCode());
			param.put("colName", menuColumnEntity.getColName());
			List<MenuColumnEntity> list = menuColumnService.select(param);
			if (list != null && list.size() > 0) {
				for (MenuColumnEntity role : list) {
					if (role.getId() != menuColumnEntity.getId()) {
						doResult(response, false, null, "该编码和名称已存在！");
						return;
					}
				}
			}
			// 更新
			menuColumnEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = menuColumnService.update(menuColumnEntity);
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

	// 删除某个菜单列的信息
	@ApiOperation(value = "删除菜单功能信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结菜单列信息")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", required = true) Long id) {
		try {
			int temp = menuColumnService.deleteById(id);
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
	
	// 启用、冻结菜单功能
	@ApiOperation(value = "冻结启用菜单功能" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用菜单功能")
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="菜单功能的ID",required=true)@RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true)@RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = menuColumnService.enableOrFreeze(id, status);
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

	// 根据菜单id删除某个菜单下的所有列
	@ApiOperation(value = "根据菜单id删除某个菜单下的所有列" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "根据菜单id删除某个菜单下的所有列")
	@RequestMapping(value = "/deletebatch")
	@ResponseBody
	public void deletebatch(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="menuId",value="菜单ID",required=true)@RequestParam(value = "menuId", required = true) Long menuId) {
		try {
			int temp = menuColumnService.deleteByMenuId(menuId);
			if (temp > 0) {
				doResult(response, true, null, "删除成功");
			} else {
				doResult(response, false, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping("/selectByMenuId")
	@ApiOperation(value = "根据菜单id查询功能列表" ,httpMethod = "GET" , produces = "application/json;charset=UTF-8" , notes = "根据菜单id查询功能列表")
	public void selectByMenuId(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="menuId",value="菜单ID",required=true)@RequestParam(value = "menuId", required = true) Long menuId) {
		try {
			if(menuId == null){
				doResult(response, false, "", "未获取到菜单ID参数");
			}
			List<MenuColumnEntity> columns =  menuColumnService.selectByMenuId(menuId);
			doResult(response, true, columns, "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
