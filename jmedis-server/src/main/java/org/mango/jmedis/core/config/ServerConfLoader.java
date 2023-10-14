package org.mango.jmedis.core.config;


import org.mango.jmedis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 服务器配置加载器
 * @Date 2023-10-14
 * @Created by mango
 */
public class ServerConfLoader {

    private static Logger log = LoggerFactory.getLogger(ServerConfLoader.class);

    // 单例
    private ServerConfLoader(){}

    private static ServerConf serverConf = ServerConf.getConf();

    // 类型映射
    private static Map<String,Class> typeMap = new HashMap<>();
    private static List<String> excludeSet = Collections.unmodifiableList(Arrays.asList("serverConf"));

    // 转换方法映射
    private static Map<Class,Method> parseMap = new HashMap<>();

    static {
        try {
            parseMap.put(Integer.class,Integer.class.getMethod("parseInt",String.class));
            parseMap.put(Boolean.class,Boolean.class.getMethod("parseBoolean", String.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Field[] fields = ServerConf.class.getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> !excludeSet.contains(field.getName()))
                .forEach(e -> {
                    typeMap.put(e.getName(), e.getType());
                });
    }

    /**
     * 加载配置文件
     */
    public static void loadServerConf(String conf) {
        try {
            if (StringUtil.isNotBlank(conf)) {
                File confFile = new File(conf);
                if (confFile.exists() && confFile.isFile()) {
                    serverConf.setConfigFile(conf);

                    // 加载配置文件
                    Properties confProp = new Properties();
                    FileInputStream fis = new FileInputStream(confFile);
                    confProp.load(fis);
                    Set<String> propSet = confProp.stringPropertyNames();
                    for(String underlineKey : propSet){
                        // 属性名
                        String key = StringUtil.underlineToCamel(underlineKey);
                        // 类型
                        Class type = typeMap.get(key);
                        Method setMethod = serverConf.getClass().getMethod(getSetMethodStr(key), type);
                        if(null != setMethod){
                            Object value = confProp.get(underlineKey);
                            if(parseMap.containsKey(type)){
                                value = parseMap.get(type).invoke(value,value);
                            }
                            // 反射设置值
                            setMethod.invoke(serverConf,value);
                        }
                    }
                    //加载完成关闭文件流
                    fis.close();
                    log.info("load conf from file {} success,conf is {}", conf, serverConf.toString());
                } else {
                    log.error("conf {} is not exists", conf);
                }
            } else {
                log.info("load default conf,conf is {}",serverConf.toString());
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

}
