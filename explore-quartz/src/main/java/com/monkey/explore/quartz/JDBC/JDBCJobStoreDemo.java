package com.monkey.explore.quartz.JDBC;

import com.monkey.explore.quartz.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author by zhangchun
 * @since 2021/1/5 3:05 下午
 */
public class JDBCJobStoreDemo {
    public static void main(String[] args)throws Exception {
        final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        final JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "myJobGroup1")
                .usingJobData("job_param", "job_param1")
                .build();
        final SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "myTriggerGroup1")
                .usingJobData("job_trigger_param", "job_trigger_param1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);

    }
}
