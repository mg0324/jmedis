package org.mango.jmedis.command.impl.server;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

import java.util.Set;

/**
 * @Description flushdb 命令
 * @Date 2021-10-31 09:16
 * @Created by mango
 */
@Cmd
public class FlushDbCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        // 清除当前数据库的key
        Memory.clearAllKeys(client.getDbIndex());
        return this.renderOk();
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,0);
    }

}
