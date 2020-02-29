package com.version.network.tcp;

public class TcpFactory {
	/**
	 * 获取netty TCPIP 客服端
	 * @return
	 */
	private static TcpClient getTcpClient() {
		return TcpClient.getTcpClient;
	}
	private static TcpSever getTcpSever() {
		return TcpSever.getTcpSever;
	}
	
	public  static void start (boolean isTcpSever,String netIp,String netPort) {
		if(isTcpSever) {
			 getTcpSever().start(netIp,netPort);
		}else {
			 getTcpClient().start(netIp,netPort);
		}
	}
}
