package org.mango.jmedis.memory.local;

import java.util.Date;

/**
 * @Description 基础数据类型
 * @Date 2021-10-31 17:28
 * @Created by mango
 */
public abstract class BaseData {
    /**
     * 超时时间
     */
    protected Date expireTime;

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int second) {
        long time = System.currentTimeMillis() + second * 1000;
        this.expireTime = new Date(time);
    }
}
