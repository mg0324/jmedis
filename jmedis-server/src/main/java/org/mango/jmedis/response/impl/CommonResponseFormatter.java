package org.mango.jmedis.response.impl;

import org.mango.jmedis.response.BaseResponseFormatter;
import org.mango.jmedis.response.CmdResponse;

/**
 * @Description 通用格式化器
 * @Date 2021-10-23 15:52
 * @Created by mango
 */
public class CommonResponseFormatter extends BaseResponseFormatter {

    @Override
    public String render(CmdResponse cmdResponse) {
        return this.renderSimple(cmdResponse);
    }
}
