package com.version.common.work;

/**
 * 队列任务 任务提交到队列后依次执行
 */
public abstract class AbstractQueueWork implements Work{
	private static final long serialVersionUID = -2082817496371756569L;

	public abstract long getId();
}
