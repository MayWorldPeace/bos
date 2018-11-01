package quartz.demo2;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import quartz.job.HelloJob;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-31 22:57
 **/
public class Demo2 {
    public static void main(String[] args) throws SchedulerException {
        // 定时器对象
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 定义一个工作对象
        JobDetail demo2 = JobBuilder.newJob(HelloJob.class).withIdentity("demo2", "group2").build();
        // 定义触发器
       Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2").startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)).build();

        scheduler.scheduleJob(demo2, trigger2);
        // 开启定时任务
        scheduler.start();
    }
}
