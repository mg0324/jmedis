package org.mango.jmedis.command.support;

import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.config.ServerConf;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;

import java.io.IOException;

/**
 * @Description exit 命令
 * @Date 2021-10-24 22:54
 * @Created by mango
 */
@Slf4j
public class ExitCmd extends BaseCmd<String> {
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
    public String name() {
        return JMedisConstant.CMD_SELECT;
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,0);
    }
}
