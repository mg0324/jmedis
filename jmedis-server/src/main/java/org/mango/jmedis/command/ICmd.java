package org.mango.jmedis.command;

import org.mango.jmedis.response.CmdResponse;

/**
 * @Description 命令接口
 * @Date 2021-10-22 23:04
 * @Created by mango
 */
public interface ICmd<T> {
    /**
     * 命令执行
     * @param index 数据库下标
     * @param param 命令参数
     * @return 返回结果
     */
    CmdResponse<T> execute(int index,String[] param);
}
