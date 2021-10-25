package org.mango.jmedis.constant;

/**
 * @Description 常量
 * @Date 2021-10-21 23:35
 * @Created by mango
 */
public class JMedisConstant {

    /**
     * 有客户端连接事件
     */
    public final static String EVENT_ACCEPT = "1";
    /**
     * 有命令请求事件
     */
    public final static String EVENT_COMMAND_REQUEST = "2";
    /**
     * 有命令答复事件
     */
    public final static String EVENT_COMMAND_RESPONSE = "3";
    /**
     * 换行
     */
    public final static String BR = "\r\n";

    /**
     * 命令响应 error类型
     */
    public final static String RESPONSE_ERROR = "error";
    /**
     * 命令响应 空类型
     */
    public final static String RESPONSE_EMPTY = "";
    /**
     * 命令响应 null类型
     */
    public final static String RESPONSE_NULL = "null";
    /**
     * 命令响应 string类型
     */
    public final static String RESPONSE_STRING = "strings";
    /**
     * 命令响应 integer类型
     */
    public final static String RESPONSE_INTEGER = "integer";

    /**
     * 命令响应 list类型
     */
    public final static String RESPONSE_LIST = "list";
}
