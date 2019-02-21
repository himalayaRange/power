package com.bly.common.utils;

import java.io.File;
import java.net.URL;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
/**
 * 
 * ========================================================
 * 日 期：2017年11月3日
 * 作 者：wangyi
 * 版 本：1.0.0
 * 类说明： 配置文件读取工具类 
 * TODO
 * ========================================================
 * 修订日期 :   
 * 修订人 :
 * 描述:
 */
public class ConfigurationUtil {
	
	/**
	 * 读取XML配置文件
	 * @param resourcePath 文件路径
	 * @return
	 */
	public static XMLConfiguration  getXMLConfiguration(String resourcePath){
		try {
			XMLConfiguration xmlConfiguration = new XMLConfiguration(resourcePath);
			return xmlConfiguration;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取XML配置文件
	 * @param file 文件
	 * @return
	 */
	public static XMLConfiguration  getXMLConfiguration(File file){
		try {
			XMLConfiguration xmlConfiguration = new XMLConfiguration(file);
			return xmlConfiguration;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取配置文件
	 * @param url URL路径
	 * @return
	 */
	public static XMLConfiguration  getXMLConfiguration(URL url){
		try {
			XMLConfiguration xmlConfiguration = new XMLConfiguration(url);
			return xmlConfiguration;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取properties配置文件
	 * @param rootPath 文件根目录
	 * @return
	 */
	public static Configuration getPropertiesConfiguration(String rootPath){
		try {
			PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(rootPath);
			return propertiesConfiguration;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取properties配置文件
	 * @param file 文件
	 * @return
	 */
	public static Configuration getPropertiesConfiguration(File file){
		try {
			PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(file);
			return propertiesConfiguration;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取properties配置文件
	 * @param url url路径
	 * @return
	 */
	public static Configuration getPropertiesConfiguration(URL url){
		try {
			PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(url);
			return propertiesConfiguration;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
}
