package com.version.network.tcp;

import lombok.Data;

@Data
public abstract class TcpAccepter implements ITcpAccepter {
	private String netIp;
	private String netPort;

	public TcpAccepter() {
		
	}

	public TcpAccepter  initServer(String netIp, String netPort) {
		this.netIp = netIp;
		this.netPort = netPort;
		return this;
	}
	
}
