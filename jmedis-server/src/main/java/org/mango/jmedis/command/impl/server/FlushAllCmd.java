package org.mango.jmedis.command.impl.server;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description flushall 命令
 * @Date 2021-10-31 16:47
 * @Created by mango
 */
@Cmd
public class FlushAllCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        // 清除所有数据库的key
        Memory.clearAllKeys();
        return this.renderOk();
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,0);
    }

}
