package com.bly.common.service.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.bly.common.interfaces.entity.BaseEntity;

/**
 * 
 * @Desc : 基础mapper
 * @author : Administrator
 * @date : 2017年8月15日
 */
public interface BaseMapper {
	public Serializable count(Map<String, Object> param);

	public <T extends BaseEntity> List<T> select(Map<String, Object> param);
	
	public <T extends BaseEntity> List<T> largeDataSelect(Map<String, Object> param);

	public <T extends BaseEntity> Integer update(T t);

	public <T extends BaseEntity> Integer insert(T t);

	public Integer delete(@Param("id") Long id);

	public <T extends BaseEntity> T find(@Param("id") Long id);
}
