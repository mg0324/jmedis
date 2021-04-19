package org.mango.jmedis.core;

import java.io.IOException;

public interface IServer {
    void start(int port);
    void stop() throws IOException;
}
