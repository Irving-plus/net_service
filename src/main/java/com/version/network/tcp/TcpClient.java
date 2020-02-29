package com.version.network.tcp;

import com.version.common.util.LoggerUtil;

/**
 * @author 周希来 /
 */
public class TcpClient extends TcpAccepter{

	public static TcpClient	 getTcpClient  = new TcpClient();
	
	private TcpClient() {
	
	}

	@Override
	public void afterAccepter() {
		LoggerUtil.info("========================================客户端启动成功");
		
	}

	@Override
	public void beforeAccepter() {
		LoggerUtil.info("======================================== 开始启动客户端");
		
	}

	@Override
	public void start(String netIp,String netPort) {
		beforeAccepter();
		try {
			//启动服务器
			initServer(netIp, netPort);
			//开启netty服务器启动监听
			TcpManager.getManager().createClientTcp(super.getNetIp(), super.getNetPort());
			afterAccepter();
		} catch (Exception e) {
			System.exit(0);
		}
		
		
	}

}
