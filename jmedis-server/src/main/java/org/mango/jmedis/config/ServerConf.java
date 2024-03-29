package org.mango.jmedis.config;


import org.mango.jmedis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Description 服务器配置
 * @Date 2021-10-23 14:53
 * @Created by mango
 */
public class ServerConf {

    private static Logger log = LoggerFactory.getLogger(ServerConf.class);


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
    public final String VERSION = "1.0.0";
    /**
     * 配置文件
     */
    public String CONFIG_FILE;
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
    private String authPasswd = "";
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

    public String getAuthPasswd() {
        return authPasswd;
    }

    public void setAuthPasswd(String authPasswd) {
        this.authPasswd = authPasswd;
    }

    /**
     * 加载配置文件
     */
    public static void loadServerConf(String conf) {
        try {
            if (StringUtil.isNotBlank(conf)) {
                File confFile = new File(conf);
                if (confFile.exists() && confFile.isFile()) {
                    ServerConf.getConf().CONFIG_FILE = conf;
                    // 类型映射
                    Map<String,Class> typeMap = new HashMap<>();
                    typeMap.put("int",Integer.class);
                    typeMap.put("string",String.class);
                    typeMap.put("boolean",Boolean.class);
                    // 转换方法映射
                    Map<String,Method> parseMap = new HashMap<>();
                    parseMap.put("int",Integer.class.getMethod("parseInt",String.class));
                    parseMap.put("boolean",Boolean.class.getMethod("parseBoolean", String.class));
                    // 加载配置文件
                    Properties confProp = new Properties();
                    FileInputStream fis = new FileInputStream(confFile);
                    confProp.load(fis);
                    Set<String> propSet = confProp.stringPropertyNames();
                    for(String e : propSet){
                        // 类型
                        String type = e.split("@")[0];
                        // 属性名
                        String key = e.split("@")[1];
                        Method setMethod = getConf().getClass().getMethod(getSetMethodStr(key),typeMap.get(type));
                        if(null != setMethod){
                            Object value = confProp.get(e);
                            if(parseMap.containsKey(type)){
                                value = parseMap.get(type).invoke(value,value);
                            }
                            // 反射设置值
                            setMethod.invoke(getConf(),value);
                        }
                    }
                    //加载完成关闭文件流
                    fis.close();
                    log.info("load conf from file {} success,conf is {}", conf, getConf().toString());
                } else {
                    log.error("conf {} is not exists", conf);
                }
            } else {
                log.info("load default conf,conf is {}",getConf().toString());
            }
        }catch (Exception e){
            log.error("load conf error,{}",e.getMessage(),e);
        }
    }

    /**
     * 返回setMethod
     * @param prop
     * @return
     */
    private static String getSetMethodStr(String prop){
        return "set" + prop.substring(0,1).toUpperCase() + prop.substring(1);
    }


    @Override
    public String toString() {
        return "ServerConf{" +
                "dbSize=" + dbSize +
                ", bufferSize=" + bufferSize +
                ", authPasswd='" + authPasswd + '\'' +
                '}';
    }
}
