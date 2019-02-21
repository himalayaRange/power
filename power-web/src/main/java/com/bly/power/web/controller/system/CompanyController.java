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
import com.bly.power.interfaces.entity.system.CompanyEntity;
import com.bly.power.interfaces.service.CompanyService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @Desc : 公司管理
 * @author : Administrator
 * @date : 2017年8月21日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API + "/system/company")
@Api(value = "CompanyController" , description = "公司管理接口")
public class CompanyController extends BaseController {
	@Autowired
	private CompanyService companyService;

	/**
	 * 公司管理页
	 */
	@ApiIgnore
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("===公司管理========");
		return "/system/company/index";
	}

	/**
	 * 获取公司列表
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "查询公司信息列表" , httpMethod = "GET"  ,  notes = "根据参数查询公司列表" , produces = "application/json;charset=UTF-8")
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response,
			//@RequestParam(name = "page", required = true) Integer page,
			//@RequestParam(name="rows", required=true)Integer rows
			@ApiParam(value="当前页数" , required=true)@RequestParam(name = "page", required = true) Integer page,
			@ApiParam(value="每页显示条数" , required=true)@RequestParam(name="rows", required=true)Integer rows
			) {
		try {
			//request.setAttribute("page", page);
			//request.setAttribute("rows", rows);
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<CompanyEntity> pages = companyService.selectPage(param);
			doResult(response, true, pages, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增公司信息
	 * 
	 * @param request
	 * @param response
	 * @param companyEntity
	 */
	@ApiOperation(value = "新增公司" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "新增公司")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, CompanyEntity companyEntity) {
		try {
			if (companyEntity == null || StringUtils.isEmpty(companyEntity.getComCode())
					|| StringUtils.isEmpty(companyEntity.getComName())) {
				doResult(response, false, null, "编码和名称不能为空！");
				return;
			}
			//校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("comCode", companyEntity.getComCode());
			param.put("comName", companyEntity.getComName());
			List<CompanyEntity> list = companyService.select(param);
			if(list != null && list.size()>0){
				doResult(response, false, null, "编码和名称已存在！");
				return;
			}
			//保存
			companyEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			companyEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = companyService.insert(companyEntity);
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
	 * 修改公司信息
	 * 
	 * @param request
	 * @param response
	 * @param companyEntity
	 */
	@ApiOperation(value = "更新公司信息" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "修改公司信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, CompanyEntity companyEntity) {
		try {
			// 条件判断
			if (companyEntity == null || companyEntity.getId() == null || companyEntity.getId() <= 0) {
				doResult(response, false, null, "公司id不能为空！");
				return;
			}
			if (companyEntity == null || StringUtils.isEmpty(companyEntity.getComName())
					|| StringUtils.isEmpty(companyEntity.getComCode())) {
				doResult(response, false, null, "名称或编码不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("comCode", companyEntity.getComCode());
			param.put("comName", companyEntity.getComName());
			List<CompanyEntity> list = companyService.select(param);
			if (list != null && list.size() > 0) {
				for (CompanyEntity role : list) {
					if (role.getId() != companyEntity.getId()) {
						doResult(response, false, null, "该编码和名称已存在！");
						return;
					}
				}
			}
			// 更新
			companyEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = companyService.update(companyEntity);
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

	// 启用、冻结公司
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	@ApiOperation(value = "冻结启用公司" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用公司")
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="公司的ID",required=true)@RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true)@RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = companyService.enableOrFreeze(id,status);
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
	
	// 删除某个公司的信息(只有冻结后的才能删除)
	@ApiOperation(value = "删除公司信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结公司信息")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="公司的ID",required=true)@RequestParam(value = "id", required = true) Long id
			) {
		try {
			if(id==null){
				doResult(response, false, null, "未获取到ID");
			}
			int temp = companyService.deleteById(id);
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
	
	@ApiOperation(value="查询公司列表下拉框" , httpMethod="GET" ,notes="用于新增编辑部门时获取所属公司下拉框")
	@RequestMapping(value="/selectCompanyListInfo")
	@ResponseBody
	public void selectCompanyListInfo(HttpServletRequest request , HttpServletResponse response){
		try {
			String fun = request.getParameter("fun");
			Map<String,Object> paramter = Maps.newHashMap();
			paramter.put("fun", fun);
			List<Map<String, Object>> companys = companyService.selectCompanyListInfo(paramter);
			doResult(response, true, companys, "查询成功");
		}catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
