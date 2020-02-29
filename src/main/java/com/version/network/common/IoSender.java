package com.version.network.common;

import java.nio.ByteBuffer;

import javax.websocket.Session;

import com.alibaba.fastjson.JSON;
import com.version.common.util.ByteUtil;
import com.version.common.util.LoggerUtil;
import com.version.network.netty.TcpMessage;

import io.netty.channel.Channel;

public class IoSender {

	public static void sendWebsocketMsg(Session session, int code,  Object obj){
		try {
			if (session.isOpen()) {
				byte[] bytes = ByteUtil.toBytesByObj(obj);
				ByteBuffer byteBuffer = ByteBuffer.allocate(8 + bytes.length);
				byteBuffer.put(ByteUtil.toBytesByObj(code));
				byteBuffer.put(ByteUtil.toBytesByObj(8 + bytes.length));
				byteBuffer.put(bytes);
				byteBuffer.flip();
				session.getBasicRemote().sendBinary(byteBuffer);

			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error(e.getMessage());
			LoggerUtil.error("io异常sendWebsocketMsg");
		}
	}
	/**
	 * tcp发送消息
	 * @param session
	 * @param code
	 * @param obj
	 */
	public static void sendTcpMsg(Channel  session, int code, Object obj) {
		try {
			if(session.isOpen()) {
				byte[] bytes =  JSON.toJSONString(obj).getBytes();
				TcpMessage message = new TcpMessage();
				message.setCode(code);
				message.setData(bytes);
				message.setLength(bytes.length +8);
				session.writeAndFlush(message);
			}
		} catch (Exception e) {
			e.printStackTrace();

			LoggerUtil.error(e.getMessage());
			LoggerUtil.error("io异常sendTcpMsg");

		}
		
		
	}
}
