package com.version.common.entity.client;

import javax.websocket.Session;

public class WebSocketClient extends AbstractClient {
	private static final long serialVersionUID = 4908615652004598245L;
	private transient Session session;
	private String endPoint;

	public String getEndPoint() {
		return endPoint;
	}

	public WebSocketClient(Session session, String endPoint) {
		super();
		this.session = session;
		this.endPoint = endPoint;
		this.sessionId = this.session.getId();
		this.ip = "";
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
