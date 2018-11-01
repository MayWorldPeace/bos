package quartz.demo3;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import quartz.job.HelloJob;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-31 23:13
 **/
public class Demo3 {
    public static void main(String[] args) throws SchedulerException {
        //定义定时器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //定义工作对象
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job", "group").build();
        //定义触发器
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 26,29 23 * * ? *"))
                .build();
        //开启定时器
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    }
}
