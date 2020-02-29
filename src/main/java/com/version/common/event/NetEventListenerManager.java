package com.version.common.event;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.version.common.find.Config;
import com.version.common.find.AbstractDynamicFind;
import com.version.common.util.LoggerUtil;
import com.version.common.util.StringHelper;

@SuppressWarnings("rawtypes")
public class NetEventListenerManager extends AbstractDynamicFind {
	private NetEventListenerManager() {
		LoggerUtil.info("初始化事件监听管理器");
	}

	private static NetEventListenerManager callBackListenerManager = new NetEventListenerManager();

	public static NetEventListenerManager getManager() {
		return callBackListenerManager;
	}

	private final Map<String, NetEventListener> netEventListeners = new HashMap<String, NetEventListener>();
	private final Map<String, NetCallbackEventListener> callBackListeners = new HashMap<String, NetCallbackEventListener>();

	@Override
	public void afterFind() {
		LoggerUtil.info("netEventListeners:"+JSONObject.toJSONString(netEventListeners));
		LoggerUtil.info("callBackListeners:"+JSONObject.toJSONString(callBackListeners));
	}

	@Override
	public void beforeFind() {
			
	}

	@Override
	public boolean verification(Class<?> clazz) {
		return interfaceOn(clazz, NetEventListener.class)
				|| interfaceOn(clazz, NetCallbackEventListener.class);
	}

	@Override
	public void findClass(Class<?> clz) throws Exception {
		if (interfaceOn(clz, NetEventListener.class)) {
			NetEventListener netEventListener = (NetEventListener) clz
					.newInstance();
			netEventListeners.put(StringHelper.buildKey(netEventListener
					.netEventType().getEventType(),
					Config.SERVER_TYPE), netEventListener);
		} else {
			NetCallbackEventListener netCallbackEventListener = (NetCallbackEventListener) clz
					.newInstance();
			callBackListeners.put(StringHelper.buildKey(
					netCallbackEventListener.netEventType().getEventType(),
					Config.SERVER_TYPE), netCallbackEventListener);
		}
	}

	public NetEventListener getNetEventListener(NetEventType netEventType,
			String serverType) {
		return netEventListeners.get(StringHelper.buildKey(
				netEventType.getEventType(), serverType));
	}

	public NetCallbackEventListener getNetCallbackEventListener(
			NetEventType netEventType, String serverType) {
		return callBackListeners.get(StringHelper.buildKey(
				netEventType.getEventType(), serverType));
	}
}
