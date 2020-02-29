package com.version.common.entity;

import com.version.common.entity.client.AbstractClient;

public abstract class AbstractController implements UniqueId<String> {
	private static final long serialVersionUID = -2142601998874865442L;
	protected transient AbstractClient superClient;
	// 最后一次登出的时间
	protected Long lastLoginOutTime;
	protected final Object controllerLock = new Object();

	public AbstractClient getSuperClient() {
		return superClient;
	}

	public void setSuperClient(AbstractClient superClient) {
		this.superClient = superClient;
	}

	public Long getLastLoginOutTime() {
		return lastLoginOutTime;
	}

	public void setLastLoginOutTime(Long lastLoginOutTime) {
		this.lastLoginOutTime = lastLoginOutTime;
	}

	public Object getControllerLock() {
		return controllerLock;
	}
}
