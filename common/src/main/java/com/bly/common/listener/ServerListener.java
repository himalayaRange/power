package com.bly.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月19日 上午10:19:49 
 * Created by Wang.Yi
 */
public class ServerListener implements ServletContextListener{
	private static final Logger logger = LoggerFactory.getLogger(ServerListener.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("=================================");
		logger.info("系统[{}]开始启动!!!", sce.getServletContext().getServletContextName());
		logger.info("=================================");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("=================================");
		logger.info("系统[{}]销毁完成!!!", sce.getServletContext().getServletContextName());
		logger.info("=================================");
	}

}
