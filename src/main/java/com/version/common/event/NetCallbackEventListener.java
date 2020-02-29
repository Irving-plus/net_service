package com.version.common.event;

public interface NetCallbackEventListener<T> {
	public NetEventType netEventType();

	public T notifyEvent(Object... objs) throws Exception;

	public boolean asynchronous();
}
