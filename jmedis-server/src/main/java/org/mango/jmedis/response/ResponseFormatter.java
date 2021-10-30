package org.mango.jmedis.response;

import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.response.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 响应格式化
 * @Date 2021-10-23 10:46
 * @Created by mango
 */
public class ResponseFormatter {

    private Map<String,IResponseFormatter> formatterMap;

    public ResponseFormatter(){
        formatterMap = new HashMap<>();
        // 注册格式化器
        formatterMap.put(JMedisConstant.RESPONSE_ERROR,new CommonResponseFormatter());
        formatterMap.put(JMedisConstant.RESPONSE_NULL,new CommonResponseFormatter());
        formatterMap.put(JMedisConstant.RESPONSE_EMPTY,new EmptyResponseFormatter());
        formatterMap.put(JMedisConstant.RESPONSE_STRING,new CommonResponseFormatter());
        formatterMap.put(JMedisConstant.RESPONSE_INTEGER,new CommonResponseFormatter());
        formatterMap.put(JMedisConstant.RESPONSE_LIST,new ListResponseFormatter());
        formatterMap.put(JMedisConstant.RESPONSE_INFO,new InfoResponseFormatter());
    }

    /**
     * 按类型格式化
     * @param cmdResponse 响应数据对象
     * @return 格式化好的字符串
     */
    public String format(CmdResponse cmdResponse){
        return formatterMap.get(cmdResponse.getType()).render(cmdResponse);
    }
}
