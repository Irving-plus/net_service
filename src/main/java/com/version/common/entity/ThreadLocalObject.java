package com.version.common.entity;

import com.version.game.Room;
import lombok.Data;

/**
 * @Author 周希来
 * @Date 2019/9/2 14:50
 */
@Data
public class ThreadLocalObject {
	private Room room;
	private AbstractController controller;
	private long beginTime;

}
