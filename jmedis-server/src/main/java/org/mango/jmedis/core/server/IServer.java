package org.mango.jmedis.core.server;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public interface IServer {
    void start(int port);
    ServerSocketChannel getServer();
    Selector getSelector();
    int getPort();
    void stop() throws IOException;
}
