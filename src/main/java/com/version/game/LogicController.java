package com.version.game;

import com.version.common.entity.AbstractController;

public class LogicController extends AbstractController {
	
	private static final long serialVersionUID = -1982109289223510826L;
	private String uniqueId;
	/**
	 * 最后一次进入的房间
	 */
	private String roomId;
	
	
	public String getRoomId() {
		return roomId;
	}


	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}


	@Override
	public String getUniqueId() {
		
		return uniqueId;
	}

}
