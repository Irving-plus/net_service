package com.version.common.find;

public interface Find {
	public void beforeFind();
	public void find() throws Exception;
	public boolean verification(Class<?> clazz);
	public void afterFind();
	public void start() throws Exception;
}
