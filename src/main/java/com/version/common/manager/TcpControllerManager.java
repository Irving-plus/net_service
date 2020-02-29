package com.version.common.manager;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.version.common.annotation.IProcess;
import com.version.common.annotation.TCPController;
import com.version.common.find.AbstractDynamicFind;

public class TcpControllerManager extends AbstractDynamicFind {
	private static final Map<String, Class<?>> controllerClazzMap = new ConcurrentHashMap<String, Class<?>>();
	private static final Map<Integer, Method>  methodMap = new ConcurrentHashMap<Integer, Method>();

	private TcpControllerManager() {
	}

	private static TcpControllerManager processManager = new TcpControllerManager();

	public static TcpControllerManager getManager() {
		return processManager;
	}
	@Override
	public boolean verification(Class<?> clazz) {
		return annotationOn(clazz, TCPController.class);
	}

	
	
	@Override
	public void findClass(Class<?> clz) throws Exception {
		TCPController controller = clz.getAnnotation(TCPController.class);
		String name = controller.name();
		if(controllerClazzMap.containsKey(name)) {
			throw new Exception("扫描出重复的TcpController:"+name);
		}
		controllerClazzMap.put(name, clz);
	
		Method[] methods =  clz.getMethods();
		for(Method method: methods) {
			IProcess iProcess = method.getAnnotation(IProcess.class);
			if(null!=iProcess) {
				int code = method.getDeclaredAnnotation(IProcess.class).code();
				if(methodMap.containsKey(code)) {
					throw new Exception("扫描出重复的消息号:"+name+":"+code);
				}
				if(method.getParameterTypes().length>1) {
					throw new Exception("处理消息的参数至多一个:"+name+":"+method.getName());
				}
				System.err.println(method);
				methodMap.put(code, method);
			}
		
		}
	}

	public Class<?> getController(int code) {
		return methodMap.get(code).getDeclaringClass();
	}
	
	public Method getProcess(int code) {
		return methodMap.get(code);
	}
	
	
	@Override
	public void afterFind() {
	}

	@Override
	public void beforeFind() {
	}
}
