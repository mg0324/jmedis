package org.mango.jmedis.ehandler;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.server.IServer;

import java.io.IOException;

/**
 * @Description 事件处理器接口
 * @Date 2021-10-21 23:43
 * @Created by mango
 */
public interface EventHandler {
     /**
      * 接收服务器连接
      * @param server 服务器
      * @throws IOException
      */
     default void handle(IServer server) throws IOException{}

     /**
      * 处理读请求
      * @param server 服务器
      * @param client
      * @throws IOException
      */
     default void handle(IServer server,JMedisClient client) throws IOException {}

     /**
      * 处理写请求
      * @param client 客户端
      * @param msg 数据
      * @throws IOException
      */
     default void handle(JMedisClient client, String msg) throws IOException {}
}
