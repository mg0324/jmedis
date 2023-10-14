package org.mango.jmedis.server;

import org.mango.jmedis.core.JMedisClient;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 服务器接口
 */
public interface IServer {
    /**
     * 监听端口并启动
     * @param port
     */
    void start(int port);

    /**
     * 获取服务器对象
     * @return
     */
    ServerSocketChannel getServer();

    /**
     * 获取多路复用器对象
     * @return
     */
    Selector getSelector();

    /**
     * 获取服务器端口
     * @return
     */
    int getPort();

    /**
     * 停止服务
     * @throws IOException
     */
    void stop() throws IOException;

    /**
     * 返回数据给客户端
     * @param client 客户端
     * @param msg 数据
     * @throws IOException
     */
    void render(JMedisClient client, String msg) throws IOException;

    /**
     * 保存客户端
     * @param client 客户端
     */
    void addClient(JMedisClient client) throws IOException;

    /**
     * 获取客户端
     * @param key 客户端的key
     * @return
     */
    JMedisClient getClient(String key);
}
