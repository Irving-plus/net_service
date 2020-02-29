package com.version.network.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author 周希来
 */
public class NetContext implements ApplicationContextAware,
		ApplicationListener<ContextRefreshedEvent> {
	public static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
//		org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@35229f85: 
		applicationContext = context;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<?> clz) {
		Object obj = applicationContext.getBean(clz);
		if (obj == null) {
			String clzName = clz.getSimpleName();
			String first = String.valueOf(clzName.charAt(0));
			clzName = first.toLowerCase() + clzName.substring(1);
			obj = applicationContext.getBean(clzName);
		}
		return (T) obj;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	}
}
