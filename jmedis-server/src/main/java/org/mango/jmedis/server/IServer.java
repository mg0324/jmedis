package org.mango.jmedis.server;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public interface IServer {
    void start(int port);
    ServerSocketChannel getServer();
    Selector getSelector();
    int getPort();
    void stop() throws IOException;
    void render(SocketChannel socketChannel, String msg) throws IOException;
}
