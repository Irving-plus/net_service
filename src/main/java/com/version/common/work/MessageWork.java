package com.version.common.work;

import java.lang.reflect.Method;

import com.version.common.entity.client.AbstractClient;
import com.version.common.entity.message.IMessage;
import org.springframework.util.StringUtils;

import com.version.common.entity.AbstractController;
import com.version.common.entity.ThreadLocalObject;
import com.version.common.manager.CacheServer;
import com.version.common.manager.RoomManager;
import com.version.common.manager.ThreadLocalManager;
import com.version.common.util.LoggerUtil;
import com.version.common.util.SpringContextUtils;
import com.version.game.LogicController;
import com.version.game.Room;

public class MessageWork implements Work{

	
	private static final long serialVersionUID = 252564223713197295L;

	private Object data;
	private Method method ;
	private AbstractClient superClient;
	private long beginTime;
	private ThreadLocalObject threadLocalObject;
	private IMessage msg;
	/**
	 * 业务线程调用
	 */
	@Override
	public void run() {
		try {
			//LoggerUtil.info("消息号"+msg.getCode());
			//LoggerUtil.info("请求的数据:"+ JSON.toJSONString(msg.getData((Class) method.getGenericParameterTypes()[0])));

			ThreadLocalManager.getThreadLocalManager().setThreadLocal(threadLocalObject);
			Thread.currentThread().setName(Thread.currentThread().getName()+":"+method.getName());
			method.invoke(SpringContextUtils.getBean(method.getDeclaringClass()),data);
			//LoggerUtil.info("业务执行时间"+(System.currentTimeMillis() -beginTime)+"毫秒");
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
			e.printStackTrace();
		} 
	}
	/**
	 * netty线程组调用
	 */
	@Override
	public void init(Object... objs) throws Exception {

		 this.method = (Method)objs[0];
		 this.msg  = (IMessage)objs[1];
		 this.superClient = (AbstractClient)objs[2];
		 this.beginTime = (Long)objs[3];
		 this.data  = msg.getData((Class) method.getGenericParameterTypes()[0]);
		 int code = msg.getCode();
		 ThreadLocalObject localObject = new ThreadLocalObject();

		 //200加入逻辑服 
		 //初始化用户,房间信息
		 if(code !=200) {
			 AbstractController controller = CacheServer.getOnlineaccounts().get(superClient.getSessionId());
			 if(controller instanceof LogicController) {
				 LogicController logicController = (LogicController) controller;	
				 String roomId= logicController.getRoomId();
				 if(!StringUtils.isEmpty(roomId)) {
					 Room room = RoomManager.getRooms().getRoomById(logicController.getRoomId());	
					 localObject.setRoom(room);
				 }
				 localObject.setController(logicController);
			}else {
				localObject.setController(controller);
			}
		 }
		 localObject.setBeginTime(beginTime);		
		this.threadLocalObject =localObject;
		
	}

}
