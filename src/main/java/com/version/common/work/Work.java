package com.version.common.work;

import java.io.Serializable;

public abstract interface Work extends Runnable,Serializable {
	public void init(Object... objs) throws Exception;
}
