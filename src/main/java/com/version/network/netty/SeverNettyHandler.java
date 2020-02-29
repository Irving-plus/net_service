package com.version.network.netty;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONObject;
import com.version.common.entity.client.AbstractClient;
import com.version.common.entity.client.TcpSocketClient;
import com.version.session.ServerSessionManager;
import com.version.common.manager.TcpControllerManager;
import com.version.common.util.ConstantUtil;
import com.version.common.util.LoggerUtil;
import com.version.common.work.MessageWork;
import com.version.common.work.WorkManager;
import com.version.network.common.IoSender;
import com.version.network.common.NetContext;
import com.version.service.api.INetEventService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author 周希来
 */
public class SeverNettyHandler extends SimpleChannelInboundHandler<TcpMessage> {
	private INetEventService netEventService = NetContext.getInstance(INetEventService.class);
	private int readIdleTimes;
	public SeverNettyHandler(){
		
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LoggerUtil.info("服务器接受客户请求");
		initSession(ctx.channel());
	}

	private void initSession(Channel session) throws Exception {
		TcpSocketClient tcpSocketClient = new TcpSocketClient(session);
		ServerSessionManager.getManager().putClient(tcpSocketClient);// 将连接加入本地缓存管理器
		LoggerUtil.info("初始化本地session"+session.toString());
		tcpSocketClient.setAttribute(ConstantUtil.LAST_RECIVED_TIME, System.currentTimeMillis());
		netEventService.sessionCreated(tcpSocketClient);
		
		//IoSender.sendMsg(session., 1001, new String("发送心跳").getBytes() );
		//返回心跳
//		ResConnectCreat.Builder builder = ResConnectCreat.newBuilder();
//		builder.setNow(System.currentTimeMillis());
//		builder.setHeartbeartTime(ConstantUtil.TCP_HEARBEAT_TIME);
//		IoSender.sendMsg(session, GameCode.RES_CONNECT_CREATE_VALUE, builder);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		LoggerUtil.info("服务器连接断开   {}", ctx);
		close(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause != null && cause.getMessage() != null) {
			 LoggerUtil.info("服务异常: {},cause: {}", ctx.channel().toString(),
					 cause.getMessage());
			close(ctx);
		}
	}

	private void close(ChannelHandlerContext ctx) throws Exception {
		AbstractClient superClient = ServerSessionManager.getManager().findClientBySession(ctx.channel());
		ServerSessionManager.getManager().closeClient(superClient);
		LoggerUtil.info("关闭链接");
	}

	/**
	 * 接收消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void channelRead0(ChannelHandlerContext ctx, TcpMessage msg) throws Exception {
		long beginTime = System.currentTimeMillis();
		LoggerUtil.info("Server messageReceived: {},message: {}", ctx.channel().toString(), JSONObject.toJSONString(msg));
		AbstractClient superClient = ServerSessionManager.getManager().findClientBySession(ctx.channel());
		superClient.setAttribute(ConstantUtil.LAST_RECIVED_TIME, System.currentTimeMillis());
		System.out.println("netty线程"+Thread.currentThread().getName());
		readIdleTimes ++;
		int code = msg.getCode();
		Method method =  TcpControllerManager.getManager().getProcess(code);
		//设置心跳次数
		//readIdleTimes = 0;
		if (method != null) {
			// 逻辑业务异步处理,动态组装方法参数
			MessageWork messageWork = new MessageWork();
			//初始化用户加入逻辑服
			messageWork.init(method,msg,superClient,beginTime);
		
			WorkManager.getManager().submit(messageWork);
		} else {
			LoggerUtil.error("请求找不到处理器,消息号:" + msg.getCode());
		}
	}
	/**
	 * 心跳检查
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		 IdleStateEvent event = (IdleStateEvent)evt;

	        String eventType = null;
	        switch (event.state()){
	            case READER_IDLE:
	                eventType = "读空闲";
	                //readIdleTimes ++; // 读空闲的计数加1
	    	      /*  LoggerUtil.info(ctx.channel().remoteAddress() + "超时事件：" +eventType);
	    	        LoggerUtil.info("超时次数:"+readIdleTimes);*/
	                break;
	            case WRITER_IDLE:
	                eventType = "写空闲";
	                // 不处理
	                break;
	            case ALL_IDLE:
	                eventType ="读写空闲";
	                // 不处理
	                break;
	        }

	        if(readIdleTimes > 3000){
	           LoggerUtil.info(" [server]读空闲超过3次，关闭连接");
	            IoSender.sendTcpMsg(ctx.channel(), 200, "心跳超时踢出服务器链接");
	            close(ctx);
	            ctx.channel().close();
	        }
		//super.userEventTriggered(ctx, evt);
	}
	
	

}
