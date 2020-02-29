package com.version.common.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.version.common.entity.AbstractController;

public class CacheServer {
	private static final Map<Object, AbstractController> onlineAccounts = new ConcurrentHashMap<Object, AbstractController>();
	private static final Map<Object, AbstractController> offlineAccounts = new ConcurrentHashMap<Object, AbstractController>();
	private static final CacheServer cache = new CacheServer();

	private CacheServer() {
	}

	public static CacheServer getCache() {
		return cache;
	}

	public static Map<Object, AbstractController> getOnlineaccounts() {
		return onlineAccounts;
	}

	public void online(AbstractController controller) {
		String id = controller.getUniqueId();
		onlineAccounts.put(id, controller);
		offlineAccounts.remove(id);
	}

	public void offline(AbstractController controller) {
		String id = controller.getUniqueId();
		// 设置最后一次登出时间
		controller.setLastLoginOutTime(System.currentTimeMillis());
		// 加入离线缓存
		offlineAccounts.put(id, controller);
		// 移除在线缓存
		onlineAccounts.remove(id);
	}

	public static Map<Object, AbstractController> getOfflineaccounts() {
		return offlineAccounts;
	}

}
