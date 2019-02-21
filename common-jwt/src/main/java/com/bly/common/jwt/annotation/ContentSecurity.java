package com.bly.common.jwt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.bly.common.jwt.enums.ContentSecurityAway;

/**
 * <p>
 * 内容安全验证注解.
 * 拦截器主动对注解的方法进行处理.
 * </p>
 * Created by wangyi.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContentSecurity {
	/**
	 * 默认加密方式 DES
	 * @return
	 */
	ContentSecurityAway away() default ContentSecurityAway.DES;
}
