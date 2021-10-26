package org.mango.jmedis.command.connection;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.response.CmdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description quit 命令
 * @Date 2021-10-24 22:54
 * @Created by mango
 */
@Cmd
public class QuitCmd extends BaseCmd<String> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        String clientKey = "";
        try {
            clientKey = client.getClientKey();
            client.close();
        } catch (IOException e) {
            log.error("close client[{}] error",clientKey,e);
        }
        return null;
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,0);
    }
}
