package org.mango.jmedis.command.string;

import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.datatype.SDS;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description set 命令
 * @Date 2021-10-23 10:02
 * @Created by mango
 */
public class SetCmd extends BaseCmd<String> {
    /**
     * eg: set a 1
     * @param index 数据库下标
     * @param param 命令参数
     * @return
     */
    @Override
    public CmdResponse<String> execute(int index,String[] param) {
        if(param.length == 2){
            String key = param[0];
            String value = param[1];
            SDS keySds = new SDS(key);
            SDS valueSds = new SDS(value);
            // 将数据存储到对应下标的数据库中
            Memory.storeString(index,keySds,valueSds);
            return this.renderOk();
        }else{
            //错误的参数个数
            return this.renderUseError(ErrorEnum.PARAM_WRONG.getMsg());
        }
    }
}
