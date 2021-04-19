package org.mango.jmedis;
import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.core.IServer;
import org.mango.jmedis.core.netty.NettyServer;

@Slf4j
public class StartupApplication {
    public static void main(String[] args) {
        //IServer server = new NioServer();
        IServer server = new NettyServer();
        server.start(9999);
    }
}
