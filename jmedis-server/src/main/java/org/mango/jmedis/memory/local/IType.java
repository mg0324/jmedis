package org.mango.jmedis.memory.local;

import java.util.Date;

/**
 * @Description 数据类型
 * @Date 2021-10-23 14:58
 * @Created by mango
 */
public interface IType {
    /**
     * 数据类型名称
     * @return
     */
    String typeName();

    /**
     * 获取过期时间
     * @return
     */
    Date getExpireTime();

    /**
     * 设置过期时间
     * @param second 秒
     */
    void setExpireTime(int second);
}
