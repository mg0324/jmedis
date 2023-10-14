package org.mango.jmedis.response.impl;

import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.response.BaseResponseFormatter;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description 空类型返回结果格式化器
 * @Date 2021-10-23 10:40
 * @Created by mango
 */
public class EmptyResponseFormatter extends BaseResponseFormatter {
    @Override
    public String render(CmdResponse cmdResponse) {
        return cmdResponse.getResult().toString() + JMedisConstant.BR;
    }
}
