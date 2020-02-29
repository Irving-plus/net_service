package com.version.common.entity.client;

import java.net.InetSocketAddress;

import com.version.common.entity.AttributeKeyConst;

import io.netty.channel.Channel;

public class TcpSocketClient extends AbstractClient {
	private static final long serialVersionUID = -2360318604421494124L;
	private transient Channel session;

	public TcpSocketClient(Channel session) {
		this.session = session;
		if (this.session != null) {
			session.attr(AttributeKeyConst.VERSION_UNIQUE_SESSION_PK_CLIENT).set(this.getSessionId());
			InetSocketAddress address= (InetSocketAddress)session.remoteAddress();
			this.ip=address.getAddress().getHostAddress();
		}
	}

	public Channel getSession() {
		return session;
	}

	public void setSession(Channel session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return "TcpSocketClient [session=" + session + "]";
	}
}
