package com.bly.common.interfaces.service;

import java.util.List;
import java.util.Map;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.utils.PageUtils;

/**
 * 
 * @Desc   : 基础service类:包括通用的增删改查和分页查询
 * @author : Administrator
 * @date   : 2017年8月15日
 */
public interface BaseService {
	/**
	 * 分页查询
	 * @param param 参数
	 * @return
	 */
	public <T extends BaseEntity> PageUtils<T> selectPage(Map<String, Object> param);

	/**
	 * 分页查询 返回object
	 * @param param 参数
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <Object> PageUtils<Object> selectPageInfo(Map<String, Object> param);
	
	/**
	 * 大数据量分页查询
	 * @param param
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <Object> PageUtils<Object> selectLargeDataPageInfo(Map<String,Object> param);
	
	/**
	 * 查询功能，返回list
	 * @param param 查询参数
	 * @return
	 */
	public <T extends BaseEntity> List<T> select(Map<String, Object> param);

	/**
	 * 获取记录数
	 * @param param  参数
	 * @return
	 */
	public long count(Map<String, Object> param);

	/**
	 * 删除操作
	 * @param id 操作对象id
	 * @return
	 */
	public int delete(Long id);

	/**
	 * 插入记录
	 * @param t 具体对象
	 * @return
	 */
	public <T extends BaseEntity> int insert(T t);

	/**
	 * 修改操作
	 * @param t 处理对象
	 * @return
	 */
	public <T extends BaseEntity> int update(T t);

	/**
	 * 获取特定对象
	 * @param id
	 * @return
	 */
	public <T extends BaseEntity> T find(long id);
}
