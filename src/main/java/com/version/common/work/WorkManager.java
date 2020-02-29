package com.version.common.work;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.version.common.find.AbstractDynamicFind;
import com.version.common.util.LoggerUtil;

public class WorkManager extends AbstractDynamicFind {
	private final Map<String, Class<? extends Work>> allWorks = new ConcurrentHashMap<String, Class<? extends Work>>();
	private static WorkManager workManager = new WorkManager();
	private final Map<Long, WorkerQueue> workerQueues;
	private final ExecutorService executor;

	public static WorkManager getManager() {
		return workManager;
	}

	private WorkManager() {
		//定义线程池名称
		ThreadFactory threadFactory = new ThreadFactoryBuilder()
		        .setNameFormat("work-%d").build();
		this.executor =new ThreadPoolExecutor(10, 200,  0L, TimeUnit.MILLISECONDS,
		        new LinkedBlockingQueue<Runnable>(1024), threadFactory, new WorkManagerPoolAbortPolicy());
		
		this.workerQueues = new ConcurrentHashMap<Long, WorkerQueue>();
	}


	
	public void submit(Work work, Object... objs) throws Exception {
		Class<? extends Work> clz = allWorks.get(work.getClass()
				.getSimpleName());
		if (clz == null) {
			allWorks.put(work.getClass().getSimpleName(), work.getClass());
			clz = work.getClass();
		}
		work.init(objs);
		submit(work);
	}

	public void submit(Class<? extends Work> workClass, Object... objs)
			throws Exception {
		Class<? extends Work> clz = allWorks.get(workClass.getSimpleName());
		if (clz == null) {
			allWorks.put(workClass.getSimpleName(), workClass);
			clz = workClass;
		}
		Work work = clz.newInstance();
		work.init(objs);
		submit(work);
	}

	public void submit(Work work) {
		if (work instanceof AbstractAynWork) {
			AbstractAynWork aynWork = (AbstractAynWork) work;
			executor.submit(aynWork);
		} else if (work instanceof AbstractQueueWork) {
			AbstractQueueWork queueWork = (AbstractQueueWork) work;
			long queueId = queueWork.getId();
			synchronized (workerQueues) {
				WorkerQueue worker = workerQueues.get(queueId);
				if (worker == null) {
					worker = new WorkerQueue(queueId);
					workerQueues.put(queueId, worker);
					worker.getQueueWorks().offer(queueWork);
					executor.submit(worker);
				}else {
					worker.getQueueWorks().offer(queueWork);
				}
			}
		} else if (work instanceof Runnable) {
			Runnable r = (Runnable) work;
			executor.submit(r);
		} else {
			throw new RuntimeException(work.getClass().getSimpleName()
					+ " work's type can not found");
		}
	}

	private class WorkerQueue extends AbstractAynWork {
		private static final long serialVersionUID = 5492935692105924020L;
		private final long queueId;
		private final LinkedBlockingDeque<AbstractQueueWork> queueWorks;

		@SuppressWarnings("unused")
		public long getWorkQueue() {
			return queueId;
		}

		public WorkerQueue(long queueId) {
			this.queueId = queueId;
			queueWorks = new LinkedBlockingDeque<AbstractQueueWork>();
		}

		public LinkedBlockingDeque<AbstractQueueWork> getQueueWorks() {
			return queueWorks;
		}

		@Override
		public void init(Object... objs) throws Exception {
		}

		@Override
		public void run() {
			while (true) {
				AbstractQueueWork queueWork = null;
				try {
					queueWork = queueWorks.take();
					queueWork.run();
				} catch (Exception e) {
					LoggerUtil.error(e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void findClass(Class<?> clazz) throws Exception {
		Class<? extends Work> clz = (Class<? extends Work>) clazz;
		this.allWorks.put(clazz.getSimpleName(), clz);
	}

	@Override
	public boolean verification(Class<?> clazz) {
		return superClassOn(clazz, AbstractQueueWork.class)
				|| superClassOn(clazz, AbstractAynWork.class)
				|| superClassOn(clazz, TimeWork.class);
	}

	@SuppressWarnings("unchecked")
	public <T> T submitCallable(Class<?> clz, Object... objs) throws Exception {
		Callable<T> callable = (Callable<T>) clz.newInstance();
		if (callable instanceof Work) {
			Work work = (Work) callable;
			work.init(objs);
			Future<T> future = this.executor.submit((Callable<T>) work);
			return future.get();
		} else {
			throw new RuntimeException("must implements Work interface!!!");
		}
	}

	@Override
	public void afterFind() {
	}

	@Override
	public void beforeFind() {
	}
}
