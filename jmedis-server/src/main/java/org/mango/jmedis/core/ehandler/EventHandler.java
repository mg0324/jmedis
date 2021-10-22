package org.mango.jmedis.core.ehandler;

import org.mango.jmedis.core.server.IServer;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @Description 事件处理器接口
 * @Date 2021-10-21 23:43
 * @Created by mango
 */
public interface EventHandler {
     default void handle(IServer server) throws IOException{}
     default void handle(SocketChannel socketChannel) throws IOException {}
}
