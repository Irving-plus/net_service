package com.version.game;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable{

	private static final long serialVersionUID = 8984115053973657790L;
	private String roomId;
	private List<LogicController> players;
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public List<LogicController> getPlayers() {
		return players;
	}
	public void setPlayers(List<LogicController> players) {
		this.players = players;
	}
	
	
}
