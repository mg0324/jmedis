package org.mango.jmedis.command;

/**
 * @Description 命令接口
 * @Date 2021-10-22 23:04
 * @Created by mango
 */
public interface ICmd {
    /**
     * 命令执行
     * @param param 命令参数
     * @return 返回结果
     */
    String execute(String[] param);
}
