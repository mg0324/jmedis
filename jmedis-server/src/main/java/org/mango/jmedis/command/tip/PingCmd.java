package org.mango.jmedis.command.tip;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description ping cmd实现
 * @Date 2021-10-22 23:22
 * @Created by mango
 */
public class PingCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        return this.renderUseEmpty("PONG");
    }
}
