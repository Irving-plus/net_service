package com.version.network.tcp;

public interface ITcpAccepter {
	
	void afterAccepter();
	void beforeAccepter();
	void start(String netIp,String netPort) throws Exception;
}
