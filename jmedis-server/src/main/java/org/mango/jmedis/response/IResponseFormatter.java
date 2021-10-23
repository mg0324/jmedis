package org.mango.jmedis.response;

/**
 * @Description 返回接口
 * @Date 2021-10-23 10:40
 * @Created by mango
 */
public interface IResponseFormatter {
    /**
     * 返回结果格式处理
     * @param cmdResponse
     * @return
     */
    String render(CmdResponse cmdResponse);
}
