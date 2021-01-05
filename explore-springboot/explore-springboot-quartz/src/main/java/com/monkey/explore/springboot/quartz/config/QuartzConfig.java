package com.monkey.explore.springboot.quartz.config;

import com.monkey.explore.springboot.quartz.job.MyJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by zhangchun
 * @since 2021/1/5 3:28 下午
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail myJobDetail(){
        return JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob1", "myJobGroup1")
                .usingJobData("job_param", "job_param1")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myTrigger(){
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myTrigger1","myTriggerGroup1")
                .usingJobData("job_trigger_param","job_trigger_param1")
                .startNow()
                //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                .build();
    }
}
