package com.heeexy.example.engine.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * config工具类
 * 读取config.properties
 * @author 
 * @createTime 
 * @history 1.修改时间,修改;修改内容：
 * 
 */
public class PropsUtil {
	private static Logger logger = LoggerFactory.getLogger(PropsUtil.class);
	
	private PropsUtil() {
	}

	private static Properties props = new Properties();
	
	static {
		ClassLoader loader = PropsUtil.class.getClassLoader();
		InputStreamReader reader = null;
		if (loader !=null){
			try {
//				reader = new InputStreamReader(loader.getResourceAsStream("file:config/cloudwalk.properties"), "UTF-8");
				reader = new InputStreamReader(loader.getResourceAsStream("cloudwalk.properties"), "UTF-8");
				props.load(reader);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}
				}
			}
		}
	}

	public static String getProperty(String key) {		
		return props.getProperty(key);
	}

	public static int getIntProperty(String key) {
		return Integer.valueOf(getProperty(key));
	}
	
}
