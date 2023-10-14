package org.mango.jmedis.core.config;

/**
 * @Description 服务器配置
 * @Date 2021-10-23 14:53
 * @Created by mango
 */
public class ServerConf {

    // 单例
    private ServerConf(){}

    private static ServerConf serverConf;

    static{
        serverConf = new ServerConf();
    }

    public static ServerConf getConf(){
        return serverConf;
    }

    /**
     * 版本
     */
    private final String version = "1.0.0";
    /**
     * 配置文件
     */
    private String configFile;
    /**
     * 端口
     */
    private Integer port = 8000;
    /**
     * 默认数据库大小
     */
    private Integer dbSize = 16;
    /**
     * 缓冲区大小，默认4M
     */
    private Integer bufferSize = 4 * 1024;
    /**
     * 数据库密码，默认为空
     */
    private String authPassword = "";
    /**
     * 定时任务线程数，默认核数+1
     */
    private Integer scheduledThreadNum = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * 线程任务线程数，默认核数+1
     */
    private Integer threadNum = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * 处理过期的key的大小，默认15
     */
    private Integer handleExpireSize = 15;

    public Integer getHandleExpireSize() {
        return handleExpireSize;
    }

    public void setHandleExpireSize(Integer handleExpireSize) {
        this.handleExpireSize = handleExpireSize;
    }

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }

    public Integer getScheduledThreadNum() {
        return scheduledThreadNum;
    }

    public void setScheduledThreadNum(Integer scheduledThreadNum) {
        this.scheduledThreadNum = scheduledThreadNum;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getDbSize() {
        return dbSize;
    }

    public void setDbSize(Integer dbSize) {
        this.dbSize = dbSize;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public String getVersion() {
        return version;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    @Override
    public String toString() {
        return "ServerConf{" +
                "version='" + version + '\'' +
                ", configFile='" + configFile + '\'' +
                ", port=" + port +
                ", dbSize=" + dbSize +
                ", bufferSize=" + bufferSize +
                ", authPasswd='" + authPassword + '\'' +
                ", scheduledThreadNum=" + scheduledThreadNum +
                ", threadNum=" + threadNum +
                ", handleExpireSize=" + handleExpireSize +
                '}';
    }
}
