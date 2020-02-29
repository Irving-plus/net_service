package com.version.service.api;

import com.version.common.entity.client.AbstractClient;

public interface INetEventService {

	public void sessionCreated(AbstractClient superClient) throws Exception;

	public void sessionOpened(AbstractClient superClient) throws Exception;

	public void sessionClosed(AbstractClient superClient) throws Exception;

	public void sessionExceptionCaught(AbstractClient superClient)
			throws Exception;

	public <T> T frameBeforeCreateRoomEvent(Object... objs) throws Exception;

	public <T> T gameBeforeCreateRoomEvent(Object... objs) throws Exception;

	public <T> T frameBeforeJoinRoomEvent(Object... objs) throws Exception;

	public <T> T gameBeforeJoinRoomEvent(Object... objs) throws Exception;

	public <T> T frameCreateRobotMirror(Object... objs) throws Exception;

	public void frameDissolutionRoomEvent(Object... objs) throws Exception;

	public void gameBeforeDissolutionRoomEvent(Object... objs) throws Exception;

	public void frameRoomBeginEvent(Object... objs) throws Exception;

	public void gameRoomBeginEvent(Object... objs) throws Exception;

	public void frameGoldForceExitEvent(Object... objs) throws Exception;

	public void gameGoldForceExitRoomEvent(Object... objs) throws Exception;

	public void frameHeartBeatEvent(Object... objs) throws Exception;

	public void gameHeartBeatEvent(Object... objs) throws Exception;

	public void frameRemoveOffLineControllerEvent(Object... objs)
			throws Exception;

	public void gameRemoveOffLineControllerEvent(Object... objs)
			throws Exception;

	public void frameSomeOneCreateRoomEvent(Object... objs) throws Exception;

	public void gameSomeOneCreateRoomEvent(Object... objs) throws Exception;

	public void frameSomeOneExitRoomEvent(Object... objs) throws Exception;

	public void gameSomeOneExitRoomEvent(Object... objs) throws Exception;

	public void frameSomeOneJoinRoomEvent(Object... objs) throws Exception;

	public void gameSomeOneJoinRoomEvent(Object... objs) throws Exception;

	public void frameSomeOneLoginSuccessEvent(Object... objs) throws Exception;

	public void gameSomeOneLoginSuccessEvent(Object... objs) throws Exception;

	public void frameSomeOneLoginOutEvent(Object... objs) throws Exception;

	public void gameSomeOneLoginOutEvent(Object... objs) throws Exception;

	public Integer frameGetCurrentHealth(Object... objs) throws Exception;

	public Integer gameGetCurrentHealthEvent(Object... objs) throws Exception;

	public void frameSomeOneReqGameReady(Object... objs) throws Exception;

	public void gameSomeOneReqGameReadyEvent(Object... objs) throws Exception;

	public void frameSomeOneAgreeDissolutionCardRoom(Object... objs)
			throws Exception;

	public void gameSomeOneAgreeDissolutionCardRoomEvent(Object... objs)
			throws Exception;

	public void frameSomeOneReqDissolutionCardRoom(Object... objs)
			throws Exception;

	public void gameSomeOneReqDissolutionCardRoomEvent(Object... objs)
			throws Exception;

	public void frameSomeOneAddWatherList(Object... objs) throws Exception;

	public void gameSomeOneAddWatherListEvent(Object... objs) throws Exception;

	public void frameSomeOneRemoveWatcherList(Object... objs) throws Exception;

	public void gameSomeOneRemoveWatherListEvent(Object... objs)
			throws Exception;

	public Boolean frameCanRemoveWatcherFromRoom(Object... objs)
			throws Exception;

	public Boolean gameCanRemoveWatcherFromRoomEvent(Object... objs)
			throws Exception;

	public void frameReconnectJoinRoomSuccess(Object... objs) throws Exception;

	public void gameReconnectJoinRoomSuccessEvent(Object... objs)
			throws Exception;

	public <T> T gameRoomExtInfoEvent(Object... objs) throws Exception;
	
	public void frameNotifyMatchStart(Object... objs) throws Exception;

	public void gameNotifyMatchStartEvent(Object... objs)
			throws Exception;
	
	public void frameNotifyMatchRoomStart(Object... objs) throws Exception;

	public void gameNotifyMatchRoomStartEvent(Object... objs)
			throws Exception;
	
	public void frameNotifyMatchOver(Object... objs) throws Exception;

	public void gameNotifyMatchOverEvent(Object... objs)
			throws Exception;

	public Boolean frameCanChangeRoomEvent(Object... objs) throws Exception;
	
	public Boolean gameCanChangeRoomEvent(Object... objs) throws Exception;
	
}
