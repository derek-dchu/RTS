package com.mercury.rts.timer;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RunMeJob extends QuartzJobBean {
	private RunMeTask runMeTask;
	
	public void setRunMeTask(RunMeTask runMeTask) {
		this.runMeTask = runMeTask;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		runMeTask.printMe();
	}

}
