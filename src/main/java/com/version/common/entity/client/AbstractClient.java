package com.version.common.entity.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.version.common.util.StringHelper;

public abstract class AbstractClient implements Serializable {
	private static final long serialVersionUID = 7907883753222206728L;
	protected String sessionId = StringHelper.randUUID();
	protected String ip;
	protected transient final Map<Object, Object> atrributes = new HashMap<Object, Object>();

	public void setAttribute(Object key, Object value) {
		this.atrributes.put(key, value);
	}

	public Object removeAttribute(Object key) {
		return this.atrributes.remove(key);
	}

	public Object getAttribute(Object key) {
		return this.atrributes.get(key);
	}

	public Map<Object, Object> getAtrributes() {
		return atrributes;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void close() {
		if (this instanceof TcpSocketClient) {
			TcpSocketClient tcpSocketClient = (TcpSocketClient) this;
			tcpSocketClient.getSession().close();
		} else if (this instanceof WebSocketClient) {
			WebSocketClient webSocketClient = (WebSocketClient) this;
			webSocketClient.close();
		}
	}
}
