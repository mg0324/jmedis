package org.mango.jmedis.response;

/**
 * @Description 基础响应格式化器
 * @Date 2021-10-23 11:31
 * @Created by mango
 */
public abstract class BaseResponseFormatter implements IResponseFormatter {
    /**
     * 通用格式化
     * @param cmdResponse
     * @return
     */
    protected String renderSimple(CmdResponse cmdResponse) {
        return "("+cmdResponse.getType()+") " + cmdResponse.getResult().toString();
    }
}
