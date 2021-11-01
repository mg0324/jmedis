package org.mango.jmedis.util;

import org.mango.jmedis.config.ServerConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description 时间任务
 * @Date 2021-11-01 22:04
 * @Created by mango
 */
public class ScheduledUtil {
    private static Logger log = LoggerFactory.getLogger(ScheduledUtil.class);
    // 定时任务
    private static ScheduledExecutorService scheduledExecutorService;
    static {
        int size = ServerConf.getConf().getScheduledThreadNum();
        scheduledExecutorService = Executors.newScheduledThreadPool(size);
        log.info("init ScheduledUtil pool threads is {}",size);
    }


    /**
     * 开始一个任务
     * period秒执行一次任务runnable
     * @param runnable 任务
     * @param period 周期
     */
    public static void start(Runnable runnable,long period){
        scheduledExecutorService.scheduleAtFixedRate(runnable,0L,period, TimeUnit.SECONDS);
    }

}
