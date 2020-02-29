package com.version.common.work;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.MutableTrigger;

import com.version.common.annotation.IInterval;
import com.version.common.find.AbstractDynamicFind;
import com.version.common.util.LoggerUtil;

public class TimerCenter extends AbstractDynamicFind {
	private Scheduler scheduled;
	public static String WORK_CLAZZ = "WORK_CLAZZ";
	private final Map<Class<? extends TimeWork>, Long> timeWorks = new HashMap<Class<? extends TimeWork>, Long>();

	private TimerCenter() {
		try {
			scheduled = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			LoggerUtil.error(e);
			System.exit(0);
		}
	}

	private static TimerCenter timerCenter = new TimerCenter();

	public static TimerCenter getCenter() {
		return timerCenter;
	}

	@Override
	public void start() throws Exception {
		try {
			super.start();
			this.scheduled.start();
			LoggerUtil.info("Timer start!");
		} catch (SchedulerException e) {
			LoggerUtil.error(e);
			throw e;
		}
	}

	public void shutdown() throws SchedulerException {
		try {
			this.scheduled.shutdown(true);
			LoggerUtil.info("Timer shutdown!");
		} catch (SchedulerException e) {
			LoggerUtil.error(e);
			throw e;
		}
	}

	public void schedueWork(Trigger trigger, Class<? extends TimeWork> clazz, JobDataMap jobDataMap) {
		try {
			TriggerKey key = trigger.getKey();
			if (this.scheduled.checkExists(key)) {
				this.scheduled.rescheduleJob(key, trigger);
				return;
			}
			JobDetail jobDetail = JobBuilder.newJob(JobImpl.class).withIdentity(key.getName(), key.getGroup()).build();
			jobDetail.getJobDataMap().put(WORK_CLAZZ, clazz);
			if (jobDataMap != null) {
				jobDetail.getJobDataMap().putAll(jobDataMap);
			}
			this.scheduled.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException("schedueWork", e);
		}
	}

	public void schedueWork(String cron, Class<? extends TimeWork> clz, JobDataMap jobDataMap)
			throws InstantiationException, IllegalAccessException {
		MutableTrigger trigger = CronScheduleBuilder.cronSchedule(cron).build();
		trigger.setKey(TriggerKey.triggerKey(clz.getSimpleName()));
		schedueWork(trigger, clz, jobDataMap);
	}

	public void deleteTimerWork(JobKey jobKey) {
		try {
			this.scheduled.deleteJob(jobKey);
		} catch (SchedulerException e) {
			throw new RuntimeException("deleteTimerWork", e);
		}
	}

	public void deleteTimerWork(Class<? extends TimeWork> clz) {
		try {
			JobKey key = new JobKey(clz.getSimpleName());
			this.scheduled.deleteJob(key);
		} catch (SchedulerException e) {
			throw new RuntimeException("schedueWork", e);
		}
	}

	public long getInterval(Class<? extends TimeWork> clazz) {
		return timeWorks.get(clazz);
	}

	@Override
	public boolean verification(Class<?> clazz) {
		return annotationOn(clazz, IInterval.class) && superClassOn(clazz, TimeWork.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void findClass(Class<?> clz) throws Exception {
		Class<? extends TimeWork> clazz = (Class<? extends TimeWork>) clz;
		IInterval interval = clazz.getAnnotation(IInterval.class);
		timeWorks.put(clazz, interval.interval());
	}

	@Override
	public void afterFind() {
	}

	@Override
	public void beforeFind() {
	}
}
