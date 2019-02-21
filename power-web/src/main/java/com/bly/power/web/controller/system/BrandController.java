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
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.utils.PageParamUtil;
import com.bly.common.utils.PageUtils;
import com.bly.common.web.controller.BaseController;
import com.bly.power.interfaces.entity.system.BrandEntity;
import com.bly.power.interfaces.service.BrandService;
import com.bly.power.web.constant.WebConstant;
import com.bly.power.web.sysUtil.SysOperationUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @Desc : 品牌controller
 * @author : Administrator
 * @date : 2017年8月22日
 */
@Controller
@RequestMapping(WebConstant.PREFIX_API + "/system/brand")
@Api(value = "/" , description = "品牌模块接口" , protocols = "http" ,hidden = true)
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;

	/**
	 * 品牌管理页
	 */
	@RequestMapping("/index")
	@ApiIgnore
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("===品牌管理========");
		return "/system/brand/index";
	}

	/**
	 * 获取品牌列表
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value="查询品牌列表" , httpMethod="GET" , notes="根据条件查询品牌" , produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(value="品牌名称" , name="brandName" ,dataType="String" ,paramType="query")
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value="当前页数" , required=true)@RequestParam(name = "page", required = true) Integer page,
			@ApiParam(value="每页显示条数" , required=true)@RequestParam(name="rows", required=true)Integer rows) {
		try {
			Map<String, Object> param = PageParamUtil.pageParam(request);
			PageUtils<BrandEntity> pages = brandService.selectPage(param);
			doResult(response, true, pages, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}

	/**
	 * 新增品牌信息
	 * 
	 * @param request
	 * @param response
	 * @param brandEntity
	 */
	@ApiOperation(value="新增品牌信息",httpMethod="POST" , produces = "application/json;charset=UTF-8" , notes = "新增品牌信息")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(HttpServletRequest request, HttpServletResponse response, BrandEntity brandEntity) {
		try {
			if (brandEntity == null || StringUtils.isEmpty(brandEntity.getBrandName())
					|| StringUtils.isEmpty(brandEntity.getBrandCode())) {
				doResult(response, false, null, "请正确填写品牌信息！");
				return;
			}
			Map<String,Object> paramter = Maps.newHashMap();
			paramter.put("brandCode", brandEntity.getBrandCode());
			List<BaseEntity> collect = brandService.select(paramter);
			if(collect != null && collect.size() > 0 ){
				doResult(response, false, null, "品牌编码重复，请修改！");
				return ;
			}
			paramter.remove("brandCode");
			paramter.put("brandName", brandEntity.getBrandName());
			List<BaseEntity> collect1 = brandService.select(paramter);
			if(collect1 != null && collect1.size() > 0 ){
				doResult(response, false, null, "品牌名称重复，请修改！");
				return ;
			}
			brandEntity.setCreator(SysOperationUtil.getCreatorOrUpdator());
			brandEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = brandService.insert(brandEntity);
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
	 * 修改品牌信息
	 * 
	 * @param request
	 * @param response
	 * @param brandEntity
	 */
	@ApiOperation(value="修改品牌信息",httpMethod="POST" , produces = "application/json;charset=UTF-8" , notes = "修改品牌信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, BrandEntity brandEntity) {
		try {
			// 条件判断
			if (brandEntity == null || brandEntity.getId() == null || brandEntity.getId() <= 0) {
				doResult(response, false, null, "品牌id不能为空！");
				return;
			}
			if (brandEntity == null || StringUtils.isEmpty(brandEntity.getBrandName())
					|| StringUtils.isEmpty(brandEntity.getBrandCode())) {
				doResult(response, false, null, "名称或编码不能为空！");
				return;
			}
			// 校验编码和名称
			Map<String, Object> param = new HashMap<>();
			param.put("brandCode", brandEntity.getBrandCode());
			param.put("brandName", brandEntity.getBrandName());
			param.put("comId", brandEntity.getComId());
			List<BrandEntity> list = brandService.select(param);
			if (list != null && list.size() > 0) {
				doResult(response, false, null, "不同公司间允许品牌名称重复，同一个公司不允许重复品牌名称!");
				return;
			}
			// 更新
			brandEntity.setUpdater(SysOperationUtil.getCreatorOrUpdator());
			int temp = brandService.update(brandEntity);
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

	// 删除某个品牌的信息
	@ApiOperation(value = "删除品牌信息" ,httpMethod = "DELETE"  , notes = "只能删除冻结品牌信息")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="品牌ID",required=true)@RequestParam(value = "id", required = true) Long id) {
		try {
			int temp = brandService.deleteById(id);
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
	@ApiOperation(value = "冻结启用品牌" ,httpMethod = "POST" , produces = "application/json;charset=UTF-8" , notes = "冻结启用品牌")
	@RequestMapping(value = "/enableOrFreeze")
	@ResponseBody
	public void enableOrFreeze(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name="id",value="品牌ID",required=true) @RequestParam(value = "id", required = true) Long id,
			@ApiParam(name="status",value="状态",required=true) @RequestParam(value = "status", required = true) Long status) {
		try {
			int temp = brandService.enableOrFreeze(id, status);
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
	@ApiOperation(value="查看品牌" , httpMethod="GET" , produces="application/json;charset=UTF-8" , notes="根据ID查询品牌")
	@ResponseBody
	@RequestMapping(value="/selectById" , method=RequestMethod.GET)
	public void selectById(HttpServletRequest request , HttpServletResponse response,
			@ApiParam(name="id",value="品牌ID",required=true)@RequestParam(value="id",required=true)Long id
			){
		try {
			if(id == null){
				doResult(response, false, null, "请传入品牌ID");
			}else{
				BrandEntity model = brandService.selectById(id);
				doResult(response, true, model, "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			doResult(response, false, null, e.getMessage());
		}
	}
}
