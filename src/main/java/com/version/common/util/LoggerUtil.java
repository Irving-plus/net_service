package com.version.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
	private static Logger logger = LoggerFactory.getLogger(LoggerUtil.class);
	
	public static void info(String str, Object... params) {
		logger.info(str, params);
	}

	public static void warn(String str, Object... params) {
		logger.warn(str, params);
	}

	public static void error(String str, Object... params) {
		logger.error(str, params);
	}
	public static void debug(String str, Object... params) {
		logger.debug(str, params);
	}
	public static void error(Exception e) {
		logger.error(ExceptionUtils.getStackTrace(e));
	}
}
