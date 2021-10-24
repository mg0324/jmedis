package org.mango.jmedis;

import org.mango.jmedis.client.ClientCommand;

public class StartupApplication {
    public static void main(String[] args) {
        args = new String[]{""};
        ClientCommand clientCommand = new ClientCommand();
        clientCommand.handle(args);
    }
}
