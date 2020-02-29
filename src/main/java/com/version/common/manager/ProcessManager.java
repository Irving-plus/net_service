package com.version.common.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.version.common.annotation.IProcess;
import com.version.common.find.AbstractDynamicFind;

public class ProcessManager extends AbstractDynamicFind {
	private static final Map<Integer, Class<?>> processClazzMap = new ConcurrentHashMap<Integer, Class<?>>();

	private ProcessManager() {
	}

	private static ProcessManager processManager = new ProcessManager();

	public static ProcessManager getManager() {
		return processManager;
	}
	@Override
	public boolean verification(Class<?> clazz) {
		return annotationOn(clazz, IProcess.class);
	}

	@Override
	public void findClass(Class<?> clz) throws Exception {
		IProcess iProcess = clz.getAnnotation(IProcess.class);
		processClazzMap.put(iProcess.code(), clz);
	}

	public Class<?> getProcess(int code) {
		return processClazzMap.get(code);
	}

	@Override
	public void afterFind() {
	}

	@Override
	public void beforeFind() {
	}
}
