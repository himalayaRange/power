package com.bly.common.service.serviceimpl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.interfaces.service.BaseService;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.utils.PageUtils;

/**
 * 
 * @Desc : 基础实现service
 * @author : Administrator
 * @date : 2017年8月15日
 */
public abstract class BaseServiceImpl implements BaseService {
	/* 多数据配置  */
	public static final String SLAVE_KBNE = "slave-kbne";
	public static final String SLAVE_AROMA = "slave-aroma";
	public static final String SLAVE_ELECTRONIC = "slave-electronic";
	
	protected abstract <T extends BaseEntity> BaseMapper getBaseMapper();

	/**
	 * 分页查询
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends BaseEntity> PageUtils<T> selectPage(Map<String, Object> param) {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		List<BaseEntity> list = select(param);
		PageUtils<T> pageUtil = new PageUtils(list, count(param));
		if(param.get("page") != null){
			pageUtil.setNowpage(Integer.parseInt(param.get("page").toString()));
		}
		if(param.get("rows") != null){
			pageUtil.setPagesize(Integer.parseInt(param.get("rows").toString()));
		}		
		if(param.get("rowStart") != null){
			pageUtil.setRowStart(Integer.parseInt(param.get("rowStart").toString()));
		}
		if(param.get("rowEnd") != null){
			pageUtil.setRowEnd(Integer.parseInt(param.get("rowEnd").toString()));
		}
		
		return pageUtil;
	}
	
	/**
	 * 分页查询 返回object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
	@Override
	public <Object> PageUtils<Object> selectPageInfo(Map<String, Object> param) {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		List<Object> list = (List<Object>) select((Map<String, java.lang.Object>) param);
		PageUtils<Object> pageUtil = new PageUtils(list, count((Map<String, java.lang.Object>) param));
		if(param.get("page") != null){
			pageUtil.setNowpage(Integer.parseInt(param.get("page").toString()));
		}
		if(param.get("rows") != null){
			pageUtil.setPagesize(Integer.parseInt(param.get("rows").toString()));
		}		
		if(param.get("rowStart") != null){
			pageUtil.setRowStart(Integer.parseInt(param.get("rowStart").toString()));
		}
		if(param.get("rowEnd") != null){
			pageUtil.setRowEnd(Integer.parseInt(param.get("rowEnd").toString()));
		}
		
		return pageUtil;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
	@Override
	public <Object> PageUtils<Object> selectLargeDataPageInfo(Map<String, Object> param) {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		List<Object> list = (List<Object>) largeDataSelect((Map<String, java.lang.Object>) param);
		PageUtils<Object> pageUtil = new PageUtils(list, count((Map<String, java.lang.Object>) param));
		if(param.get("page") != null){
			pageUtil.setNowpage(Integer.parseInt(param.get("page").toString()));
		}
		if(param.get("rows") != null){
			pageUtil.setPagesize(Integer.parseInt(param.get("rows").toString()));
		}		
		if(param.get("rowStart") != null){
			pageUtil.setRowStart(Integer.parseInt(param.get("rowStart").toString()));
		}
		if(param.get("rowEnd") != null){
			pageUtil.setRowEnd(Integer.parseInt(param.get("rowEnd").toString()));
		}
		
		return pageUtil;
	}

	/**
	 * 查询功能，返回list
	 */
	@Override
	public <T extends BaseEntity> List<T> select(Map<String, Object> param) {
		return getBaseMapper().select(param);
	}
	
	/**
	 * 大数据量分页查询
	 * @param param
	 * @return
	 */
	public <T extends BaseEntity> List<T> largeDataSelect(Map<String, Object> param){
		return getBaseMapper().largeDataSelect(param);
	}

	/**
	 * 返回总记录数
	 */
	@Override
	public long count(Map<String, Object> param) {
		Serializable count = getBaseMapper().count(param);
		if (count == null) {
			return 0L;
		} else {
			return Long.valueOf(count.toString());
		}
	}
	

	/**
	 * 删除操作
	 */
	@Override
	public int delete(Long id) {
		return getBaseMapper().delete(id);
	}

	/**
	 * 插入记录
	 */
	@Override
	public <T extends BaseEntity> int insert(T t) {
		return getBaseMapper().insert(t);
	}

	/**
	 * 更新记录
	 */
	@Override
	public <T extends BaseEntity> int update(T t) {
		return getBaseMapper().update(t);
	}

	/**
	 * 获取特定对象
	 */
	@Override
	public <T extends BaseEntity> T find(long id) {
		return getBaseMapper().find(id);
	}
}
