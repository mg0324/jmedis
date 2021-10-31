package org.mango.jmedis.command.impl.strings;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.memory.local.datatype.SDS;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description append 命令
 * @Date 2021-10-31 17:04
 * @Created by mango
 */
@Cmd
public class AppendCmd extends BaseCmd<Integer> {
    /**
     * eg: append a 123
     * @param client 客户端
     * @param param 命令参数
     * @return 字符串长度
     */
    @Override
    public CmdResponse<Integer> execute(JMedisClient client, String[] param) {
        String key = param[0];
        String value = param[1];
        SDS keySds = new SDS(key);
        SDS valueSds = new SDS(value);
        // 将数据追加到对应下标的数据库中
        Integer len = Memory.appendString(client.getDbIndex(),keySds,valueSds);
        return this.renderUseInteger(len);
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,2);
    }
}
