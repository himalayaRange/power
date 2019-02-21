package com.bly.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceChange {

	/**
	 * 是否使用从库，默认使用主库,配置false，dbName配置无效
	 * @return
	 */
	boolean slave() default false;

	/**
	 * 从库名称，针对名称进行选择
	 * @return
	 */
	String dbName();

}
