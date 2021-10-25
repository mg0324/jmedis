package org.mango.jmedis.command.strings;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.datatype.SDS;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description get 命令
 * @Date 2021-10-23 10:02
 * @Created by mango
 */
@Cmd
public class GetCmd extends BaseCmd<String> {
    /**
     * eg: get a
     * @param client 客户端
     * @param param 命令参数
     * @return
     */
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        String key = param[0];
        // 将数据存储到对应下标的数据库中
        SDS value = Memory.getString(client.getDbIndex(),key);
        if(null == value){
            return this.renderUseNull();
        }
        return this.renderUseString(value.getString());
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,1);
    }
}
