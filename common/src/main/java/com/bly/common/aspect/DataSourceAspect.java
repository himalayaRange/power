package com.bly.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.bly.common.exception.DataSourceAspectException;

@Aspect
@Component
public class DataSourceAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

	@Around("@annotation(dataSourceChange)")
	public Object doAround(ProceedingJoinPoint point, DataSourceChange dataSourceChange) {
		Object retVal = null;
		boolean selectedDataSource = false;
		try {
			if (null != dataSourceChange) {
				selectedDataSource = true;
				if (dataSourceChange.slave()) {
					DynamicDataSource.useSlave(dataSourceChange.dbName());
				} else {
					DynamicDataSource.useMaster();
				}
			} else {
				selectedDataSource = true;
				DynamicDataSource.useMaster();
			}
			retVal = point.proceed();
		} catch (Throwable e) {
			logger.error(" 数据源切换错误，FOLLOWS MSG : ", e.getMessage());
			throw new DataSourceAspectException(e);
		} finally {
			if (selectedDataSource) {
				DynamicDataSource.reset();
			}
		}
		return retVal;
	}

}
