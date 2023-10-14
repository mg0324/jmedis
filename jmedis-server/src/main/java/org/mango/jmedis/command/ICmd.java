package org.mango.jmedis.command;

import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description 命令接口
 * @Date 2021-10-22 23:04
 * @Created by mango
 */
public interface ICmd<T> {
    /**
     * 命令分发执行
     * @param client 客户端
     * @param param 命令参数
     * @return 返回结果
     */
    CmdResponse<T> dispatch(JMedisClient client, String[] param);

    /**
     * 命令执行
     * @param client 客户端
     * @param param 命令参数
     * @return 返回结果
     */
    CmdResponse<T> execute(JMedisClient client, String[] param);

    /**
     * 命令名称
     * @return
     */
    String name();

    /**
     * 命令参数期望个数
     * @param param 参数
     * @return 参数格式正确返回true，反之返回false
     */
    boolean expect(String[] param);

    /**
     * 校验参数
     * @param client 客户端
     * @param param 参数
     * @return 参数正确返回null，反之返回响应对象
     */
    CmdResponse<T> validate(JMedisClient client,String[] param);
}
