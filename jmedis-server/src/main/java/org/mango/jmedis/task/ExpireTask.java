package org.mango.jmedis.task;

import org.mango.jmedis.core.config.ServerConf;
import org.mango.jmedis.memory.Memory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Description 过期任务
 * @Date 2021-11-01 22:22
 * @Created by mango
 */
public class ExpireTask implements Runnable {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private List<Integer> indexList;
    public ExpireTask(){
        // 1.获取数据库DB
        int dbSize = ServerConf.getConf().getDbSize();
        this.indexList = new ArrayList<>();
        for(int i=0;i<dbSize;i++){
            indexList.add(i);
        }
    }
    @Override
    public void run() {
        log.debug("ExpireTask start");
        long start = System.currentTimeMillis();
        indexList.parallelStream().forEach(a->{
            Set<String> keys = Memory.getAllKeys(a);
            if(Objects.nonNull(keys) && keys.size()>0){
                for(String e : keys){
                    Memory.handleExpireKey(a,e);
                }
            }
        });
        long end = System.currentTimeMillis();
        log.debug("ExpireTask execute complete takes {} ms",(end-start));
    }
}
