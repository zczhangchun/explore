package com.monkey.explore.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

/**
 * @author by zhangchun
 * @since 2021/1/3 8:24 下午
 */
public class SimpleTriggerDemo {
    public static void main(String[] args) throws Exception{
        final Date myTime = new Date(new Date().getTime() + 1000 * 3);
        //1、指定时间戳触发一次
        //SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
        //        .withIdentity("trigger1", "group1")
        //        .startAt(myTime)
        //        .build();

        //2、指定时间触发，每隔10秒执行一次，重复10次：
        //SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
        //        .startAt(myTime)
        //        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
        //        .withIntervalInSeconds(10)
        //        .withRepeatCount(10))
        //        .build();

        //3、5分钟以后开始触发，仅执行一次：
        //SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
        //        .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE))
        //        .build();

        //4、立即触发，每个5分钟执行一次，直到22:00：
        //final SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
        //        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
        //                .withIntervalInMinutes(5)
        //                .repeatForever())
        //        .endAt(DateBuilder.dateOf(22, 0, 0))
        //        .build();

        //5、建立一个触发器，将在下一个小时的整点触发，然后每2小时重复一次：
        //final SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
        //        .startAt(DateBuilder.evenHourDate(null))
        //        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
        //                .withIntervalInHours(2))
        //        .build();

        //6、设置错误策略
        final SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(1)
                        .withMisfireHandlingInstructionNextWithExistingCount())
                .build();

    }
}
