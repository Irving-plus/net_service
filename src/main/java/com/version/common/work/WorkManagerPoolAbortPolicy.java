package com.version.common.work;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.version.common.util.LoggerUtil;

public class WorkManagerPoolAbortPolicy implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		LoggerUtil.info("线程拒绝Task " + r.toString() +
                " rejected from " +
                e.toString());
		//线程池满了自定义拒绝业务异常 
		throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                e.toString());
	}

}
