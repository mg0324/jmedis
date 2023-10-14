package org.mango.jmedis.response.impl;

import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.response.BaseResponseFormatter;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.response.bean.Info;
import org.mango.jmedis.response.bean.InfoBlock;
import java.util.List;
import java.util.Map;

/**
 * @Description info类型返回结果格式化器
 * @Date 2021-10-23 10:40
 * @Created by mango
 */
public class InfoResponseFormatter extends BaseResponseFormatter {
    @Override
    public String render(CmdResponse cmdResponse) {
        StringBuilder sb = new StringBuilder();
        Info info = (Info) cmdResponse.getResult();
        List<InfoBlock> blockList = info.getBlockList();
        for(InfoBlock block : blockList){
            sb.append("# " + block.getName() + JMedisConstant.BR);
            Map<String,String> props = block.getProps();
            for(String key : props.keySet()){
                sb.append(key).append("=").append(props.get(key)).append(JMedisConstant.BR);
            }
            sb.append(JMedisConstant.BR);
        }
        return sb.toString();
    }
}
