package org.mango.jmedis.config;

/**
 * @Description 客户端配置
 * @Date 2021-10-24 14:20
 * @Created by mango
 */
public class ClientConf {
    /**
     * program name
     */
    public static final String PROGRAM_NAME = "jmedis-cli";
    /**
     * 版本
     */
    public static final String VERSION = "0.0.1";
    /**
     * 默认host
     */
    public static final String DEFAULT_HOST = "127.0.0.1";
    /**
     * 默认端口
     */
    public static final int DEFAULT_PORT = 8000;
    /**
     * 缓存大小
     */
    public static final int BUFFER_SIZE = 1024 * 4;
}
