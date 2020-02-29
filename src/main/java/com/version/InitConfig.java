package com.version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.version.common.manager.TcpControllerManager;
import com.version.common.util.LoggerUtil;
import com.version.network.tcp.TcpFactory;

@Component
public class InitConfig implements ApplicationListener<ContextRefreshedEvent> {

	@Value(value = "${zhou.tcpHostIp}")
	private String tcpIp;
	@Value(value = "${zhou.tcpHostPort}")
	private String tcpPort;
	@Value(value = "${zhou.isTcpSever}")
	private boolean isTcpSever;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LoggerUtil.info("netty服务监听启动");
		try {
			
			TcpFactory.start(isTcpSever,tcpIp, tcpPort);
			//初始化自定义注解管理器
			//ProcessManager.getManager().start();
			TcpControllerManager.getManager().start();
			
		} catch (Exception e) {
			LoggerUtil.error("netty服务器启动失败");
			e.printStackTrace();
			System.exit(0);
		}
		
	}

}
