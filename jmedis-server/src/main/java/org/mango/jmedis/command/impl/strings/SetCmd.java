package org.mango.jmedis.command.impl.strings;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.annotation.WithExpire;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.memory.local.datatype.SDS;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description set 命令
 * @Date 2021-10-23 10:02
 * @Created by mango
 */
@Cmd
@WithExpire
public class SetCmd extends BaseCmd<String> {
    /**
     * eg: set a 1
     * @param client 客户端
     * @param param 命令参数
     * @return
     */
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        String key = param[0];
        String value = param[1];
        SDS keySds = new SDS(key);
        SDS valueSds = new SDS(value);
        // 将数据存储到对应下标的数据库中
        Memory.storeString(client.getDbIndex(),keySds,valueSds);
        return this.renderOk();
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,2);
    }
}
