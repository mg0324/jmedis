package org.mango.jmedis.constant;

/**
 * @Description 常量
 * @Date 2021-10-21 23:35
 * @Created by mango
 */
public class JMedisConstant {
    /**
     * 缓冲区大小，默认4M
     */
    public final static int BUFFER_SIZE = 4 * 1024;

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
}
