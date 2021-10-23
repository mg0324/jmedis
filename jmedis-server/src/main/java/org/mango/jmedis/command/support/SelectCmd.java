package org.mango.jmedis.command.support;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description select 命令
 * @Date 2021-10-23 22:54
 * @Created by mango
 */
public class SelectCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        if(param.length == 1){
            String index = param[0];
            try {
                int dbIndex = Integer.parseInt(index);
                client.select(dbIndex);
                return this.renderOk();
            }catch (NumberFormatException e){
                return this.renderUseError(ErrorEnum.PARAM_WRONG_TYPE.getMsg());
            }
        }else{
            //错误的参数个数
            return this.renderUseError(ErrorEnum.PARAM_WRONG_NUMBER.getMsg());
        }
    }
}
