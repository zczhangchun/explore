package com.monkey.explore.springboot.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author by zhangchun
 * @since 2021/1/5 3:27 下午
 */
public class MyJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().getString("k1"));
    }
}
