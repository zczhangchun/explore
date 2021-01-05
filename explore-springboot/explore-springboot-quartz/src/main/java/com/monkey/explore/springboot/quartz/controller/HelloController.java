package com.monkey.explore.springboot.quartz.controller;

import com.monkey.explore.springboot.quartz.job.MyJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by zhangchun
 * @since 2021/1/5 9:13 下午
 */
@RestController
public class HelloController {

    @Autowired
    private Scheduler scheduler;

    @RequestMapping("hello")
    private void hello(String value) throws SchedulerException {
        final JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity(value, value + "group")
                .usingJobData("k1", value)
                .build();

        final Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(value + "t", value + "trg1")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                .startNow()
                .build();
        scheduler.scheduleJob(jobDetail, trigger);

    }
}
