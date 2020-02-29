package com.version.common.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.version.game.Room;

public class RoomManager {
	private static final Map<String, Room> rooms = new ConcurrentHashMap<String, Room>();
	
	private static final RoomManager roomManager = new RoomManager();

	private RoomManager() {
	}

	public static RoomManager getRooms() {
		return roomManager;
	}

	public Room getRoomById(String roomId) {
		return rooms.get(roomId);
	}

}
