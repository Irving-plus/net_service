package com.version.network.netty;


import com.version.common.entity.client.AbstractClient;
import io.netty.channel.ChannelHandlerContext;

import com.alibaba.fastjson.JSONObject;
import com.version.common.entity.client.TcpSocketClient;
import com.version.common.manager.ProcessManager;
import com.version.session.ServerSessionManager;
import com.version.common.util.ConstantUtil;
import com.version.common.util.LoggerUtil;
import com.version.common.work.Work;
import com.version.common.work.WorkManager;
import com.version.network.common.NetContext;
import com.version.service.api.INetEventService;

import io.netty.channel.Channel;

import io.netty.channel.SimpleChannelInboundHandler;

public class ClientNettyHandler  extends SimpleChannelInboundHandler<TcpMessage> {
	private INetEventService netEventService = NetContext.getInstance(INetEventService.class);
	@Override
	public void channelActive(ChannelHandlerContext session) throws Exception {
		System.out.println("客户端链接服务器");
		//IoSender.sendTcpMsg(session.channel(),200,"客户端请求服务器");

		initSession(session.channel());
	}

	private void initSession(Channel session) throws Exception {
		TcpSocketClient tcpSocketClient = new TcpSocketClient(session);
		ServerSessionManager.getManager().putClient(tcpSocketClient);// 将连接加入本地缓存管理器
		tcpSocketClient.setAttribute(ConstantUtil.LAST_RECIVED_TIME, System.currentTimeMillis());
		netEventService.sessionCreated(tcpSocketClient);
		//返回心跳
//		ResConnectCreat.Builder builder = ResConnectCreat.newBuilder();
//		builder.setNow(System.currentTimeMillis());
//		builder.setHeartbeartTime(ConstantUtil.TCP_HEARBEAT_TIME);
//		IoSender.sendMsg(session, GameCode.RES_CONNECT_CREATE_VALUE, builder);
	}

	@Override
	public void channelInactive(ChannelHandlerContext session) throws Exception {
//		LoggerUtil.debug("Server  sessionClosed: {}", session);
		//AbstractClient superClient = ServerSessionManager.getManager().findClientBySession(session.channel());
		//close(superClient);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext session, Throwable cause) throws Exception {
		 LoggerUtil.info("Server exceptionCaught: {},cause: {}", session.channel(),
		 cause.getMessage());
		if (cause != null && cause.getMessage() != null) {
			AbstractClient superClient = ServerSessionManager.getManager().findClientBySession(session.channel());
			close(superClient);
		}
	}

	private void close(AbstractClient superClient) throws Exception {
		ServerSessionManager.getManager().closeClient(superClient);
	}

	/**
	 * 接收消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void channelRead0(ChannelHandlerContext session, TcpMessage msg) throws Exception {
		LoggerUtil.info("Server messageReceived: {},message: {}", session.channel(), JSONObject.toJSONString(msg));
		LoggerUtil.info("1111");

		System.out.println("客户端开始读取服务端过来的信息"+JSONObject.toJSONString(msg));
		System.out.println("字符串"+new String(msg.getData(),"utf-8"));


		AbstractClient superClient = ServerSessionManager.getManager().findClientBySession(session.channel());
		superClient.setAttribute(ConstantUtil.LAST_RECIVED_TIME, System.currentTimeMillis());
		Class<? extends Work> clazz = (Class<? extends Work>) ProcessManager.getManager().getProcess(msg.getCode());
		if (clazz != null) {
			// 逻辑业务异步处理
			Work work = clazz.newInstance();
			work.init(msg);
			WorkManager.getManager().submit(work);
		} else {
			LoggerUtil.error("请求找不到处理器,消息号:" + msg.getCode());
		}
	}
	

}
