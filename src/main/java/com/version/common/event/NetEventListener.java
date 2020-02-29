package com.version.common.event;

public interface NetEventListener {
	public NetEventType netEventType();

	public void notifyEvent(Object... objs) throws Exception;

	public boolean asynchronous();

}
