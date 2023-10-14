package org.mango.jmedis.command.impl.connection;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.annotation.NoAuth;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description echo cmd实现
 * @Date 2021-10-30 09:07
 * @Created by mango
 */
@Cmd
@NoAuth
public class EchoCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        return this.renderUseEmpty(param[0]);
    }

    @Override
    public boolean expect(String[] param) {
        // =1
        return this.sizeEq(param,1);
    }
}
