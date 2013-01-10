package com.fm.jobs;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {
	private static final Logger log = Logger.getLogger(JobScheduler.class);
	Scheduler sched = null;

	public boolean scheduleJobs() {
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			sched = sf.getScheduler();
			scheduleSimulator();
			schedulePlayerRest();
			// start the scheduler
			sched.start();
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	private void scheduleSimulator() throws Exception {
		JobDetail job = newJob(GameSimulatorJob.class)
			      .withIdentity("gameSimulator")
			      .build();
		Trigger trigger = newTrigger()
				    .withIdentity("gameSimulatorTrigger")
				    .withSchedule(cronSchedule("0 0 * * * ?"))
				    .forJob(job)
				    .build();
		sched.scheduleJob(job, trigger);
	}
	private void schedulePlayerRest() throws Exception {
		JobDetail job = newJob(PlayerRestJob.class)
		.withIdentity("playerRest")
		.build();
		Trigger trigger = newTrigger()
		.withIdentity("playerRestTrigger")
		.withSchedule(cronSchedule("0 30 * * * ?"))
		.forJob(job)
		.build();
		sched.scheduleJob(job, trigger);
	}


	public void closeJobs() {
		try {
			sched.shutdown();
		} catch (SchedulerException e) {
			log.error(e);
		}
	}

	public static void main(String[] args) {
		JobScheduler sched = new JobScheduler();
		sched.scheduleJobs();
	}
}
