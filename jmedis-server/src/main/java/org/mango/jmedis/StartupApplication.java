package org.mango.jmedis;

import org.mango.jmedis.server.IServer;
import org.mango.jmedis.server.nio.JMedisServer;

public class StartupApplication {

    public static void main(String[] args) {
        String conf = "";
        if(args.length>0){
            conf = args[0];
        }
        IServer server = new JMedisServer(conf);
        //IServer server = new NettyServer();
        server.start(8000);
    }
}
