package org.mango.jmedis.command.impl.server;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.annotation.WithExpire;
import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

import java.util.Set;

/**
 * @Description db size 命令
 * @Date 2021-10-30 09:16
 * @Created by mango
 */
@Cmd
@WithExpire
public class DbSizeCmd extends BaseCmd<Integer> {
    @Override
    public CmdResponse<Integer> execute(JMedisClient client, String[] param) {
        // 先查询当前数据库里的键值
        Set<String> keys = Memory.getAllKeys(client.getDbIndex());
        return this.renderUseInteger(keys.size());
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,0);
    }

}
