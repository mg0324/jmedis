package org.mango.jmedis;
import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.server.IServer;
import org.mango.jmedis.server.nio.JmedisServer;

@Slf4j
public class StartupApplication {
    public static void main(String[] args) {
        IServer server = new JmedisServer();
        //IServer server = new NettyServer();
        server.start(8000);
    }
}
