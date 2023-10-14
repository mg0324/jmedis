package org.mango.jmedis.util;

import org.mango.jmedis.core.config.ServerConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 线程任务
 * @Date 2021-11-02 22:19
 * @Created by mango
 */
public class ExecutorUtil {
    private static Logger log = LoggerFactory.getLogger(ExecutorUtil.class);
    // 线程任务
    private static ExecutorService executorService;
    static {
        int size = ServerConf.getConf().getThreadNum();
        executorService = Executors.newFixedThreadPool(size);
        log.info("init ExecutorUtil pool threads is {}",size);
    }


    /**
     * 开始一个任务
     * 执行一次任务runnable
     * @param runnable 任务
     */
    public static void execute(Runnable runnable){
        executorService.execute(runnable);
    }

}
