package com.version.common.entity;

import java.io.Serializable;

public interface UniqueId<T> extends Serializable{
	public T getUniqueId();
}
