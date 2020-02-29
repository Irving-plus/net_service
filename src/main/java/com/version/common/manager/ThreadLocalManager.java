package com.version.common.manager;

import com.version.common.entity.ThreadLocalObject;

/**
 * @author 周希来
 */
public class ThreadLocalManager {
	private static ThreadLocal<ThreadLocalObject> threadLocal = new ThreadLocal<>();
	      
	private static final ThreadLocalManager threadLocalManager = new ThreadLocalManager();

	private ThreadLocalManager() {
		
	}
	public static ThreadLocalManager getThreadLocalManager() {
		
		return threadLocalManager;
	}
    public void setThreadLocal(ThreadLocalObject value) {
    	
    	
         threadLocal.set(value);
    }
    public ThreadLocalObject getThreadLocal( ) {
    
      return  threadLocal.get();
   }
}
