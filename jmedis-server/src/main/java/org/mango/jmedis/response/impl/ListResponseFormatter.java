package org.mango.jmedis.response.impl;

import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.response.BaseResponseFormatter;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.StringUtil;
import java.util.List;

/**
 * @Description list类型返回结果格式化器
 * @Date 2021-10-23 10:40
 * @Created by mango
 */
public class ListResponseFormatter extends BaseResponseFormatter {
    @Override
    public String render(CmdResponse cmdResponse) {
        List<String> list = (List<String>) cmdResponse.getResult();
        if(list.size() == 0){
            return StringUtil.wrapBr("(empty array)");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<list.size();i++){
            sb.append(i+1).append(") ").append(list.get(i)).append(JMedisConstant.BR);
        }
        return sb.toString();
    }
}
