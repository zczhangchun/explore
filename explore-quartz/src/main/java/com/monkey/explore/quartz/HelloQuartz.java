package com.monkey.explore.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author by zhangchun
 * @since 2021/1/3 6:29 下午
 */
public class HelloQuartz {
    public static void main(String[] args)throws Exception{
        //创建调度器

        final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("key","value");

        //创建触发器
        final Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever())
                .build();
        trigger.getJobDataMap().put("t2", "tv2");

        //创建任务
        final JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .usingJobData("j1", "jv1")
                .withIdentity("myjob", "mygroup")
                .build();
        jobDetail.getJobDataMap().put("j2", "jv2");

        //注册触发器并启动调度器
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();


    }
}
