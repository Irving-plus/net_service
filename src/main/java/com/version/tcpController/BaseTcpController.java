package com.version.tcpController;
import com.version.common.entity.AbstractController;
import com.version.common.entity.ThreadLocalObject;
import com.version.common.manager.ThreadLocalManager;
import com.version.game.LogicController;
import com.version.game.Room;

/**
 * @Author 周希来
 * @Date 2019/9/2 14:50
 */
public class BaseTcpController {
	
	public ThreadLocalObject getThreadLocalObject() {
		return ThreadLocalManager.getThreadLocalManager().getThreadLocal();
	} 
	public Room getRoom() {
		Room room = getThreadLocalObject().getRoom();
		if(null==room) {
			new Exception("房间为null");
		}
		return getThreadLocalObject().getRoom();
	}
	public AbstractController getController() {
		AbstractController controller = getThreadLocalObject().getController();
		if(null==controller) {
			new Exception("用户是null");
		}
	
		return controller;
	}
	public LogicController getLogicController() {
		AbstractController controller = getThreadLocalObject().getController();
		if(null==controller) {
			new Exception("用户是null");
		}
		if(controller instanceof LogicController) {
			return (LogicController)controller;
		}
		return null;
		
	}
	public long getBeginTime() {
		
		return getThreadLocalObject().getBeginTime();
	}
	
}
