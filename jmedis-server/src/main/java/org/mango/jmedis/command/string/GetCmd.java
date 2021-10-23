package org.mango.jmedis.command.string;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.datatype.SDS;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description get 命令
 * @Date 2021-10-23 10:02
 * @Created by mango
 */
public class GetCmd extends BaseCmd<String> {
    /**
     * eg: get a
     * @param client 客户端
     * @param param 命令参数
     * @return
     */
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        if(param.length == 1){
            String key = param[0];
            // 将数据存储到对应下标的数据库中
            SDS value = Memory.getString(client.getDbIndex(),key);
            if(null == value){
                return this.renderUseNull();
            }
            return this.renderUseString(value.getString());
        }else{
            //错误的参数个数
            return this.renderUseError(ErrorEnum.PARAM_WRONG_NUMBER.getMsg());
        }
    }
}
