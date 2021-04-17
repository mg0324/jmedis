package org.mango.jmedis;
import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.core.NettyServer;

@Slf4j
public class StartupApplication {
    public static void main(String[] args) {
        NettyServer.start(9999);
    }
}
