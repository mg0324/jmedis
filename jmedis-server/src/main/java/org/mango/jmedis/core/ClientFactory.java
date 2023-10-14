package org.mango.jmedis.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 客户端工厂
 * @Date 2021-10-30 20:45
 * @Created by mango
 */
public class ClientFactory {
    // 客户端map
    private static Map<String, JMedisClient> CLIENT_MAP;

    static {
        CLIENT_MAP = new ConcurrentHashMap<>();
    }

    public static Map<String, JMedisClient> current() {
        return CLIENT_MAP;
    }
}
