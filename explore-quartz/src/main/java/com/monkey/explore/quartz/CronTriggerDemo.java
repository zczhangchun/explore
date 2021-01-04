package com.monkey.explore.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;

/**
 * @author by zhangchun
 * @since 2021/1/3 9:24 下午
 */
public class CronTriggerDemo {
    public static void main(String[] args) {
        //1、使用
        final CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?"))
                .build();
    }
}
