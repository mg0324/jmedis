package org.mango.jmedis.command.impl.keys;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.annotation.WithExpire;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * expire key second
 * @Description expire 命令 设置过期时间
 * @Date 2021-10-31 17:30
 * @Created by mango
 */
@Cmd
@WithExpire
public class ExpireCmd extends BaseCmd {
    @Override
    public CmdResponse execute(JMedisClient client, String[] param) {
        String key = param[0];
        String vStr = param[1];
        try {
            Integer value = Integer.parseInt(vStr);
            Integer result = Memory.expireKey(client.getDbIndex(),key,value);
            return this.renderUseInteger(result);
        }catch (NumberFormatException e){
            return this.renderUseError(ErrorEnum.PARAM_WRONG_RANGE_TYPE.getMsg());
        }
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,2);
    }

}
