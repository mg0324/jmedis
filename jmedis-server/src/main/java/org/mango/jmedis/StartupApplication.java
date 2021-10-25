package org.mango.jmedis;

import org.mango.jmedis.server.IServer;
import org.mango.jmedis.server.nio.JMedisServer;

public class StartupApplication {

    public static void main(String[] args) {
        IServer server = new JMedisServer();
        //IServer server = new NettyServer();
        server.start(8000);
    }
}
