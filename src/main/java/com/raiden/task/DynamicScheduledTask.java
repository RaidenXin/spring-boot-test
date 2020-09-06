package com.raiden.task;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
 
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
 
/**
 * Created by loup on 2017/11/11.
 */
@Component
@EnableScheduling
public class DynamicScheduledTask implements SchedulingConfigurer {
 
    //时间表达式  每2秒执行一次
    private String cron = "0/2 * * * * ?";
 
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //任务逻辑
                System.out.println("---------------start-------------------");
                System.out.println("动态修改定时任务参数，时间表达式cron为：" + cron);
                System.out.println("当前时间为：" + sdf.format(new Date()));
                System.out.println("----------------end--------------------");
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(cron);
                Date nextExecDate = cronTrigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        });
    }
 
    public void setCron(String cron) {
        this.cron = cron;
    }

    private static final String[] getTableNameAll(String data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate now = LocalDate.now();
        if (StringUtils.isBlank(data)){
            return getTableNameAll(formatter.format(now.plusMonths(-3)));
        }else {
            LocalDate localDate = LocalDate.parse(data, formatter);
            int until = (int) localDate.until(now, ChronoUnit.MONTHS) + 1;
            String[] tableNames = new String[until];
            String name = "uba_";
            tableNames[0] = name + data.substring(0, 6);
            for (int i = 1; i < until; i++){
                tableNames[i] = name + formatter.format(localDate.plusMonths(i)).substring(0, 6);
            }
            return tableNames;
        }
    }
}