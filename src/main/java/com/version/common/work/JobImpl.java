package com.version.common.work;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.version.common.util.LoggerUtil;

public class JobImpl implements Job {
	@Override
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			Class<? extends TimeWork> clazz = (Class<? extends TimeWork>) context
					.getJobDetail().getJobDataMap().get(TimerCenter.WORK_CLAZZ);
			TimeWork timeWork = clazz.newInstance();
			timeWork.init(context);
			timeWork.run();
		} catch (Exception e) {
			LoggerUtil.error(e);
		}
	}
}
