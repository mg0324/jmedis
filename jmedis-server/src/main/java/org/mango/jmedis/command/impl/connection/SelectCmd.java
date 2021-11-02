package org.mango.jmedis.command.impl.connection;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.annotation.WithExpire;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.config.ServerConf;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description select 命令
 * @Date 2021-10-23 22:54
 * @Created by mango
 */
@Cmd
@WithExpire
public class SelectCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        client.select(Integer.parseInt(param[0]));
        return this.renderOk();
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,1);
    }

    @Override
    public CmdResponse<String> validate(JMedisClient client, String[] param) {
        String index = param[0];
        try {
            int dbIndex = Integer.parseInt(index);
            // index = [0,15]
            if(dbIndex < 0 || dbIndex > ServerConf.getConf().getDbSize() - 1){
                String msg = ErrorEnum.PARAM_WRONG_RANGE.getMsg() +
                        ",expect [0,"+(ServerConf.getConf().getDbSize() - 1)+"]";
                return this.renderUseError(msg);
            }
        }catch (NumberFormatException e){
            return this.renderUseError(ErrorEnum.PARAM_WRONG_TYPE.getMsg());
        }
        return null;
    }
}
