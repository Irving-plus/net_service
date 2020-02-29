package com.version.common.work;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import com.version.common.annotation.IInterval;
import com.version.common.util.LoggerUtil;

/**
 * quartz默认开启10个线程执行所有定时任务，如果阻塞其中某个线程，其他定时器就少一个线程，
 * 所以尽量不要在此类里做耗时的操作
 */
public abstract class TimeWork extends AbstractAynWork {
	private static final long serialVersionUID = -2448950776438012061L;
	protected JobExecutionContext context;

	public abstract void execute(JobDataMap paramJobDataMap) throws Exception;

	@Override
	public void init(Object... args) throws ClassCastException {
		this.context = ((JobExecutionContext) args[0]);
	}

	public long getInterval() {
		if (!this.getClass().isAnnotationPresent(IInterval.class)) {
			throw new RuntimeException("no interval!");
		}
		IInterval iInterval = this.getClass().getAnnotation(IInterval.class);
		return iInterval.interval();
	}

	@Override
	public void run() {
		JobDataMap jobDataMap = this.context.getJobDetail().getJobDataMap();
		try {
			execute(jobDataMap);
		} catch (Exception e) {
			LoggerUtil.error(e);
		}
	}
}
