package com.bly.power.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bly.common.utils.PageParamUtil;
import com.bly.common.utils.PageUtils;
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.system.DeptEntity;
import com.bly.power.interfaces.service.DeptService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @Desc   : 部门管理
 * @author : Administrator
 * @date   : 2017年8月21日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API + "/system/dept")
@Api(value = "DeptController" , description = "部门管理接口")
public class DeptController extends BaseController {
	@Autowired
	private DeptService deptService;

	/**
	 * 公司管理页
	 */
	@ApiIgnore
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("===部门管理========");
		return "/system/dept/index";
	}

	/**
	 * 获取部门列表
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "查询部门信息列表" , httpMethod = "GET"  , notes = "根据参数查询部门列表，参数说明：{comCode：所属公司的编码 ； comName:所属公司名称}" , produces = "application/json;charset=UTF-8")
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value="当前页数" , required=true)@RequestParam(name = "page", required = true) Integer page,
			@ApiParam(value="每页显示条数" , required=true)@RequestParam(name="rows", required=true)Integer rows
			) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<DeptEntity> pages = deptService.selectPage(param);
			doResult(response, true, pages, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增部门信息
	 * 
	 * @param request
	 * @param response
	 * @param deptEntity
	 */
	@ApiOperation(value = "新增部门" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "新增部门,非必输项为冗余字段")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, DeptEntity deptEntity) {
		try {
			if (deptEntity == null || StringUtils.isEmpty(deptEntity.getDeptName())
					|| StringUtils.isEmpty(deptEntity.getDeptCode())) {
				doResult(response, false, null, "编码和名称不能为空！");
				return;
			}
			//校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("deptCode", deptEntity.getDeptCode());
			param.put("deptName", deptEntity.getDeptName());
			List<DeptEntity> list = deptService.select(param);
			if(list != null && list.size()>0){
				doResult(response, false, null, "编码和名称已存在！");
				return;
			}
			//保存
			deptEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			deptEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = deptService.insert(deptEntity);
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
	 * 修改部门信息
	 * 
	 * @param request
	 * @param response
	 * @param deptEntity
	 */
	@ApiOperation(value = "修改部门" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "修改部门,非必输项为冗余字段")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, DeptEntity deptEntity) {
		try {
			// 条件判断
			if (deptEntity == null || deptEntity.getId() == null || deptEntity.getId() <= 0) {
				doResult(response, false, null, "部门id不能为空！");
				return;
			}
			if (deptEntity == null || StringUtils.isEmpty(deptEntity.getDeptName())
					|| StringUtils.isEmpty(deptEntity.getDeptCode())) {
				doResult(response, false, null, "名称或编码不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("deptCode", deptEntity.getDeptCode());
			param.put("deptName", deptEntity.getDeptName());
			List<DeptEntity> list = deptService.select(param);
			if (list != null && list.size() > 0) {
				for (DeptEntity role : list) {
					if (role.getId() != deptEntity.getId()) {
						doResult(response, false, null, "该编码和名称已存在！");
						return;
					}
				}
			}
			// 更新
			deptEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = deptService.update(deptEntity);
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

	// 删除某个部门的信息
	@RequestMapping(value = "/delete")
	@ResponseBody
	@ApiOperation(value = "删除部门信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结部门信息")
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="部门的ID",required=true)@RequestParam(value = "id", required = true) Long id) {
		try {
			int temp = deptService.deleteById(id);
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
	
	// 启用、冻结部门
	@ApiOperation(value = "冻结启用部门" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用部门")
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
		      @ApiParam(value="部门ID",required=true)  @RequestParam(value = "id", required = true) String id,
			  @ApiParam(value="部门状态",required=true) @RequestParam(value = "status", required = true) String status) {
		try {
			if(id==null || status==null){
				doResult(response, false, null, "参数错误，请检查后再操作");
			}
			else{
				int temp = deptService.enableOrFreeze(Long.valueOf(id),Long.valueOf(status));
				if (temp > 0) {
					doResult(response, true, null, "操作成功");
				} else {
					doResult(response, false, null, "操作失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	//查看
	@ApiOperation(value="查看部门" , httpMethod="GET" , produces="application/json;charset=UTF-8" , notes="根据ID查询部门")
	@ResponseBody
	@RequestMapping(value="/selectById" , method=RequestMethod.GET)
	public void selectById(HttpServletRequest request , HttpServletResponse response,
			@ApiParam(name="id",value="部门ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入部门ID");
			}else{
				DeptEntity model = deptService.selectById(id);
				doResult(response, true, model, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	//根据公司ID查询公司下所有的部门
	@ApiOperation(value="根据公司ID查询公司下所有的部门" , httpMethod="GET" , produces="application/json;charset=UTF-8" , notes="根据公司ID查询公司下所有的部门")
	@ResponseBody
	@RequestMapping(value="/selectDeptByCompanyId" , method=RequestMethod.GET)
	public void selectDeptByCompanyId(HttpServletRequest request , HttpServletResponse response,
			@ApiParam(name="comId",value="公司ID",required=true)@RequestParam(value="comId",required=true)Long comId
			){
		try {
			if(comId == null){
				doResult(response, false, null, "请传入公司ID");
			}else{
				 List<Map<String, Object>> list = deptService.selectDeptByCompanyId(comId);
				doResult(response, true, list, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
	
	
	@ApiOperation(value="根据ID查询当前部门的下级部门" , httpMethod="GET" , produces="application/json;charset=UTF-8" , notes="根据ID查询当前部门的下级部门")
	@ResponseBody
	@RequestMapping(value="/selectSubordinateDeptById" , method=RequestMethod.GET)
	public void selectDeptByParentId(HttpServletRequest request , HttpServletResponse response,
			@ApiParam(name="id",value="部门ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入部门ID");
			}else{
				 List<Map<String, Object>> list = deptService.selectSubordinateDeptById(id);
				doResult(response, true, list, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
