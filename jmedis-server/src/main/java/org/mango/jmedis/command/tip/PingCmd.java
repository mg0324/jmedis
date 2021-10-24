package org.mango.jmedis.command.tip;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description ping cmd实现
 * @Date 2021-10-22 23:22
 * @Created by mango
 */
public class PingCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        if(param.length == 0){
            return this.renderUseEmpty("PONG");
        }else if(param.length == 1){
            return this.renderUseEmpty(param[0]);
        }
        return null;
    }

    @Override
    public String name() {
        return JMedisConstant.CMD_PING;
    }

    @Override
    public boolean expect(String[] param) {
        // <=1
        return this.sizeLe(param,1);
    }
}
