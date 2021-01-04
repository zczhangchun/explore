package com.monkey.explore.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 * @author by zhangchun
 * @since 2021/1/3 6:41 下午
 */
public class HelloJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        final Object tv1 = jobExecutionContext.getTrigger().getJobDataMap().get("t1");
        final Object tv2 = jobExecutionContext.getTrigger().getJobDataMap().get("t2");
        final Object jv1 = jobExecutionContext.getJobDetail().getJobDataMap().get("j1");
        final Object jv2 = jobExecutionContext.getJobDetail().getJobDataMap().get("j2");
        Object sv = null;
        try {
            sv = jobExecutionContext.getScheduler().getContext().get("key");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println(tv1 + "：" + tv2);
        System.out.println(jv1 + "：" + jv2);
        System.out.println(sv);
        System.out.println("hello job");
    }
}
