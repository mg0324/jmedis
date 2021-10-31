package org.mango.jmedis.command.impl.keys;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * ttl key 获取key的有效时间，单位秒
 * @Description ttl 命令 过期时间
 * @Date 2021-10-31 17:30
 * @Created by mango
 */
@Cmd
public class TtlCmd extends BaseCmd {
    @Override
    public CmdResponse execute(JMedisClient client, String[] param) {
        String key = param[0];
        Integer second = Memory.ttlKey(client.getDbIndex(),key);
        return this.renderUseInteger(second);
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,1);
    }

}
