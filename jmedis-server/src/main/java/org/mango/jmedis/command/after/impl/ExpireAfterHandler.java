package org.mango.jmedis.command.after.impl;

import org.mango.jmedis.annotation.WithExpire;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.ICmd;
import org.mango.jmedis.command.after.AfterHandler;
import org.mango.jmedis.config.ServerConf;
import org.mango.jmedis.memory.Memory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 在当前index下的数据库里找15个key，处理过期时间
 * @Description 过期时间处理器
 * @Date 2021-11-02 22:14
 * @Created by mango
 */
public class ExpireAfterHandler extends AfterHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public ExpireAfterHandler(ICmd cmd, JMedisClient client, String[] param) {
        super(cmd, client, param);
    }

    @Override
    public boolean enable(ICmd cmd, JMedisClient client, String[] param) {
        // 命令上有@WithExpire注解则启用
        WithExpire withExpire = cmd.getClass().getAnnotation(WithExpire.class);
        if(Objects.nonNull(withExpire)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean doHandle(ICmd cmd, JMedisClient client, String[] param) {
        int index = client.getDbIndex();
        // 获取数据库下有设置过期时间的keys
        Set<String> keys = Memory.getAllExpireKeys(index);
        if(Objects.nonNull(keys)){
            // 如果keys的个数小于15，则都处理掉
            int confSize = ServerConf.getConf().getHandleExpireSize();
            if(keys.size() <= confSize){
                keys.parallelStream().forEach(e->Memory.handleExpireKey(index,e));
            }else{
                // 大于，则随机选取15个
                List<String> keyList = new ArrayList<>(confSize);
                keyList.addAll(keys);
                List<Integer> indexList = new ArrayList<>(confSize);
                int size = keys.size();
                for(int i=0;i<size;i++){
                    boolean isRandom = false;
                    while (!isRandom) {
                        int random = (int) (Math.random() * size);
                        if (!indexList.contains(random)) {
                            indexList.add(random);
                            isRandom = true;
                        }
                    }
                }
                log.info("after expire random is {}",indexList.toArray());
                // 处理选中的key的expire
                indexList.parallelStream().forEach(d->Memory.handleExpireKey(index,keyList.get(d)));
            }
        }
        return false;
    }

}
