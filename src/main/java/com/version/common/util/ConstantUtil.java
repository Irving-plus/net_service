package com.version.common.util;

public class ConstantUtil {
	public static final String SYSTEM_ROOMER_ID="0";
	public static final long TCP_HEARBEAT_TIME = 5000L;
	public static final long SEND_CHAT_MSG = 788888L;
	public static final Integer MATCH_TIMES_TYPE = new Integer(10000);
	public static final int DIV_SCALE = 10;//除法精度（除不尽时保留10为小数）
	public static final long GAME_QUENE_ID_PRE=100000L;//游戏队列号基数，比如挖豹子游戏ID是8879，它的队列号为108879
	public static final String LAST_RECIVED_TIME = "LAST_RECIVED_TIME";

	public static final String VERSION_SESSION_REDIS_PALYERID = "VERSION_SESSION_REDIS_PALYERID";
	public static final String VERSION_ONLINE_SESSION_UUID = "VERSION_ONLINE_SESSION_UUID";
	public static final String VERSION_LOGIN_CURRUSER = "VERSION_LOGIN_CURRUSER";
	
	public static final String VERSION_ALAST_LOGIN_VO = "VERSION_ALAST_LOGIN_VO";
	public static final String VERSION_ROOM_LOCATION_INFO = "VERSION_ROOM_LOCATION_INFO";
	public static final String VERSION_LOGIC_RANDROOMID = "VERSION_LOGIC_RANDROOMID";
	public static final String VERSION_SERVER_INFO_IPS_SAVEVO = "VERSION_SERVER_INFO_IPS_SAVEVO";

//	public static final String VERSION_ROOM_SMALL_CAL = "VERSION_ROOM_SMALL_CAL_";
//	public static final String VERSION_ROOM_BIG_CAL = "VERSION_ROOM_BIG_CAL_";

	public static final String VERSION_SERVER_INFO_IPS = "VERSION_SERVER_INFO_IPS";
	public static final String VERSION_ATIMES_CONFIG = "VERSION_ATIMES_CONFIG";

//	public static final String VERSION_LOGIC_ONLINE_TOTAL_COUNT = "VERSION_LOGIC_ONLINE_TOTAL_COUNT_";
	public static final String VERSION_AGATE_CONFIG = "VERSION_AGATE_CONFIG";
	public static final String VERSION_AI_CONFIG = "VERSION_AI_CONFIG";
	public static final String VERSION_PROXYID = "VERSION_PROXYID";
	public static final String VERSION_AI_WIN_CONFIG = "VERSION_AI_WIN_CONFIG_";
//	public static final String VERSION_AI_WIN_RATE = "VERSION_AI_WIN_RATE";
//	public static final String VERSION_AI_WIN_TYPE = "VERSION_AI_WIN_TYPE";
	public static final String VERSION_MATCH_CONFIG = "VERSION_MATCH_CONFIG";
	public static final String VERSION_GOLBAL_MATCH_DATA = "VERSION_GOLBAL_MATCH_DATA";
}
