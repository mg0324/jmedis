package org.mango.jmedis.util;

import org.mango.jmedis.client.JMedisClient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 服务端工具
 * @Date 2021-10-30 20:45
 * @Created by mango
 */
public class ServerUtil {
    // 客户端map
    public static Map<String, JMedisClient> clientMap;

    static {
        clientMap = new ConcurrentHashMap<>();
    }
}
