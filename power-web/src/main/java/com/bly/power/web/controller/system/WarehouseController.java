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
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.system.WarehouseEntity;
import com.bly.power.interfaces.service.WarehouseService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Desc : 仓库
 * @author : Administrator
 * @date : 2017年8月23日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API + "/system/warehouse")
@Api(value = "/" , description = "仓库模块接口" , protocols = "http" ,hidden = true)
public class WarehouseController extends BaseController {

	@Autowired
	private WarehouseService warehouseService;

	/**
	 * 获取仓库列表
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "查询仓库信息列表" , httpMethod = "GET"  ,  notes = "根据参数查询仓库列表" , produces = "application/json;charset=UTF-8")
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(value="所属公司ID" ,name="comId" ,dataType="Long",paramType="query"),
		@ApiImplicitParam(value="仓库编码" , name="wareCode" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="仓库名称" , name="wareName" ,dataType="String" ,paramType="query"),
		@ApiImplicitParam(value="当前页数" , name="page" ,dataType="Integer" ,paramType="query",required=true),
		@ApiImplicitParam(value="每页显示条数" , name="rows" ,dataType="Integer" ,paramType="query",required=true)
	})
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<WarehouseEntity> pages= warehouseService.selectPage(param);
			doResult(response, true, pages, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增仓库信息
	 * 
	 * @param request
	 * @param response
	 * @param warehouseEntity
	 */
	@ApiOperation(value = "新增仓库" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "新增仓库")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, WarehouseEntity warehouseEntity) {
		try {
			if (warehouseEntity == null || StringUtils.isEmpty(warehouseEntity.getWareName())
					|| StringUtils.isEmpty(warehouseEntity.getWareCode())) {
				doResult(response, false, null, "请正确填写仓库信息！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("wareCode", warehouseEntity.getWareCode());
			param.put("wareName", warehouseEntity.getWareName());
			List<WarehouseEntity> list = warehouseService.select(param);
			if (list != null && list.size() > 0) {
				doResult(response, false, null, "编码和名称已存在！");
				return;
			}
			// 保存
			warehouseEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			warehouseEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = warehouseService.insert(warehouseEntity);
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
	 * 修改仓库信息
	 * 
	 * @param request
	 * @param response
	 * @param warehouseEntity
	 */
	@ApiOperation(value = "更新仓库信息" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "修改仓库信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, WarehouseEntity warehouseEntity) {
		try {
			// 条件判断
			if (warehouseEntity == null || warehouseEntity.getId() == null || warehouseEntity.getId() <= 0) {
				doResult(response, false, null, "仓库id不能为空！");
				return;
			}
			if (warehouseEntity == null || StringUtils.isEmpty(warehouseEntity.getWareName())
					|| StringUtils.isEmpty(warehouseEntity.getWareCode())) {
				doResult(response, false, null, "仓库编码和名称不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("wareCode", warehouseEntity.getWareCode());
			param.put("wareName", warehouseEntity.getWareName());
			List<WarehouseEntity> list = warehouseService.select(param);
			if (list != null && list.size() > 0) {
				for (WarehouseEntity role : list) {
					if (role.getId() != warehouseEntity.getId()) {
						doResult(response, false, null, "该编码和名称已存在！");
						return;
					}
				}
			}
			// 更新
			warehouseEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = warehouseService.update(warehouseEntity);
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

	// 删除某个仓库的信息
	@ApiOperation(value = "删除仓库信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结仓库信息")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="仓库的ID",required=true)@RequestParam(value = "id", required = true) Long id) {
		try {
			if(id==null){
				doResult(response, false, null, "未获取到ID");
			}
			int temp = warehouseService.deleteById(id);
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

	// 启用、冻结品牌
	@ApiOperation(value = "冻结启用仓库" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用仓库")
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="仓库的ID",required=true)@RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true)@RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = warehouseService.enableOrFreeze(id, status);
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
	
	//查看
	@ApiOperation(value="查看仓库" , httpMethod="GET" , produces="application/json;charset=UTF-8" , notes="根据ID查询仓库信息")
	@ResponseBody
	@RequestMapping(value="/selectById" , method=RequestMethod.GET)
	public void selectById(HttpServletRequest request , HttpServletResponse response,
			@ApiParam(name="id",value="仓库ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入仓库ID");
			}else{
				WarehouseEntity model = warehouseService.selectById(id);
				System.out.println(model.getCreateTime()+"---"+model.getUpdateTime());
				doResult(response, true, model, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	@ApiOperation(value="查询仓库树形结构列表" , httpMethod="GET" ,notes="用于新增编辑仓库时选择父类仓库使用")
	@RequestMapping(value="/selectWarehouseParentsInfo")
	@ResponseBody
	public void selectWarehouseParentsInfo(HttpServletRequest request , HttpServletResponse response){
		try {
			List<Map<String, Object>> selectWarehouseParentsInfo = warehouseService.selectWarehouseParentsInfo();
			doResult(response, true, selectWarehouseParentsInfo, "查询成功");
		}catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
