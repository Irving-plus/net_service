package com.version.service;

import java.util.concurrent.Callable;

import com.version.common.entity.client.AbstractClient;
import org.springframework.stereotype.Service;

import com.version.common.event.NetCallbackEventListener;
import com.version.common.event.NetEventListener;
import com.version.common.event.NetEventListenerManager;
import com.version.common.event.NetEventType;
import com.version.common.find.Config;
import com.version.common.util.LoggerUtil;
import com.version.common.work.AbstractAynWork;
import com.version.common.work.Work;
import com.version.common.work.WorkManager;
import com.version.service.api.INetEventService;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class NetEventServiceImpl implements INetEventService {

	private void notifyEvent(final NetEventListener listener, Object... objs)
			throws Exception {
		if (listener != null) {
			if (listener.asynchronous()) {
				WorkManager.getManager().submit(new AbstractAynWork() {
					private static final long serialVersionUID = -6773796283107715207L;
					Object[] objs;

					@Override
					public void run() {
						try {
							listener.notifyEvent(objs);
						} catch (Exception e) {
							LoggerUtil.error(e);
						}
					}
					@Override
					public void init(Object... objs) throws Exception {
						this.objs = objs;
					}
				}, objs);
			} else {
				listener.notifyEvent(objs);
			}
		}
	}

	@Override
	public void sessionCreated(AbstractClient superClient) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.SESSION_CREATED,
						Config.SERVER_TYPE);
		notifyEvent(listener, superClient);
	}

	@Override
	public void sessionOpened(AbstractClient superClient) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.SESSION_OPENED,
						Config.SERVER_TYPE);
		notifyEvent(listener, superClient);
	}

	@Override
	public void sessionClosed(AbstractClient superClient) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.SESSION_CLOSED,
						Config.SERVER_TYPE);
		notifyEvent(listener, superClient);
	}

	@Override
	public void sessionExceptionCaught(AbstractClient superClient)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.SESSION_EXCEPTION,
						Config.SERVER_TYPE);
		notifyEvent(listener, superClient);
	}

	public static class CallableWork<T> implements Work, Callable<T> {
		private static final long serialVersionUID = 6176316560251278573L;
		private NetCallbackEventListener<Boolean> listener;
		private Object[] objs;

		@Override
		public T call() throws Exception {
			return (T) listener.notifyEvent(objs);
		}

		@Override
		public void init(Object... objs) throws Exception {
			this.listener = (NetCallbackEventListener) objs[0];
			this.objs = (Object[]) objs[1];
		}

		@Override
		public void run() {
		}

	}

	private <T> T notifyCallBackEvent(NetCallbackEventListener listener,
			Object... objs) throws Exception {
		if (listener != null) {
			if (listener.asynchronous()) {
				return WorkManager.getManager().submitCallable(
						CallableWork.class, listener, objs);
			} else {
				return (T) listener.notifyEvent(objs);
			}
		} else {
			return null;
		}
	}

	@Override
	public <T> T gameBeforeCreateRoomEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.GAME_BEFORE_CREATE_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public <T> T gameBeforeJoinRoomEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.GAME_BEFORE_JOIN_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public <T> T frameBeforeCreateRoomEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.FRAME_BEFORE_CREATE_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public <T> T frameBeforeJoinRoomEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.FRAME_BEFORE_JOIN_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}
	@Override
	public <T> T gameRoomExtInfoEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.GAME_ROOM_EXT_INFO,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}
	@Override
	public void frameDissolutionRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_DISSOLUTION_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameBeforeDissolutionRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_BEFORE_DISSOLUTION_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameRoomBeginEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_ROOM_BEGIN,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameRoomBeginEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_ROOM_BEGIN,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameGoldForceExitEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_GOLD_FORCE_EXIT_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameGoldForceExitRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_GOLD_FORCE_EXIT_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameHeartBeatEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_HEARTBEAT_EVENT,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameHeartBeatEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_HEARBEAT_CHECK,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameRemoveOffLineControllerEvent(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.FRAME_REMOVE_OFFLINE_CONTROLLER,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameRemoveOffLineControllerEvent(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.GAME_REMOVE_OFFLINE_CONTROLLER,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneCreateRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_SOMEONE_CREATE_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneCreateRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_SOMEONE_CREATE_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneExitRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_SOMEONE_EXIT_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneExitRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_SOMEONE_EXIT_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneJoinRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_SOMEONE_JOIN_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneJoinRoomEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_SOMEONE_JOIN_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneLoginSuccessEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_SOMEONE_LOGIN_SUCESS,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneLoginSuccessEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_SOMEONE_LOGIN_SUCCESS,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneLoginOutEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_SOMEONE_LOGINOUT,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneLoginOutEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_SOMEONE_LOGINOUT,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public <T> T frameCreateRobotMirror(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.FRAME_CREATE_ROBOT_MIRROR,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public Integer frameGetCurrentHealth(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.FRAME_GET_CURRENT_HEALTH,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public Integer gameGetCurrentHealthEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.GAME_GET_CURRENT_HEALTH,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public void frameSomeOneReqGameReady(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_SOMEONE_REQ_GAMEREADY,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneReqGameReadyEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_SOMEONE_REQ_GAMEREADY,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneAgreeDissolutionCardRoom(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.FRAME_SOMEONE_AGREE_DISSOLUTION_CARD_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneAgreeDissolutionCardRoomEvent(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.GAME_SOMEONE_AGREE_DISSOLUTION_CARD_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneReqDissolutionCardRoom(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.FRAME_SOMEONE_REQ_DISSOLUTION_CARD_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneReqDissolutionCardRoomEvent(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.GAME_SOMEONE_REQ_DISSOLUTION_CARD_ROOM,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneAddWatherList(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.FRAME_SOMEONE_ADD_WATCHER_LIST,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneAddWatherListEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.GAME_SOMEONE_ADD_WATCHER_LIST,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameSomeOneRemoveWatcherList(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.FRAME_SOMEONE_REMOVE_WATCHER_LIST,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameSomeOneRemoveWatherListEvent(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(
						NetEventType.GAME_SOMEONE_REMOVE_WATCHER_LIST,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public Boolean frameCanRemoveWatcherFromRoom(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.FRAME_CAN_REMOVE_WATCHER_FROM_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public Boolean gameCanRemoveWatcherFromRoomEvent(Object... objs)
			throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.GAME_CAN_REMOVE_WATCHER_FROM_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public void frameReconnectJoinRoomSuccess(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_RECONNECT_JOIN_ROOM_SUCCESS,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameReconnectJoinRoomSuccessEvent(Object... objs)
			throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_RECONNECT_JOIN_ROOM_SUCCESS,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void frameNotifyMatchStart(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_NOTIFY_MATCH_START,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameNotifyMatchStartEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_NOTIFY_MATCH_START,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
		
	}
	
	@Override
	public void frameNotifyMatchRoomStart(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_NOTIFY_MATCH_ROOM_START,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
	}

	@Override
	public void gameNotifyMatchRoomStartEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_NOTIFY_MATCH_ROOM_START,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
		
	}

	@Override
	public void frameNotifyMatchOver(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.FRAME_NOTIFY_MATCH_OVER,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
		
	}

	@Override
	public void gameNotifyMatchOverEvent(Object... objs) throws Exception {
		final NetEventListener listener = NetEventListenerManager.getManager()
				.getNetEventListener(NetEventType.GAME_NOTIFY_MATCH_OVER,
						Config.SERVER_TYPE);
		notifyEvent(listener, objs);
		
	}

	@Override
	public Boolean frameCanChangeRoomEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.FRAME_CAN_CHANGE_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

	@Override
	public Boolean gameCanChangeRoomEvent(Object... objs) throws Exception {
		final NetCallbackEventListener listener = NetEventListenerManager
				.getManager().getNetCallbackEventListener(
						NetEventType.GAME_CAN_CHANGE_ROOM,
						Config.SERVER_TYPE);
		return notifyCallBackEvent(listener, objs);
	}

}
