package org.mango.jmedis.command.validator.impl;

import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.CmdExecutor;
import org.mango.jmedis.command.validator.CmdValidator;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.StringUtil;

/**
 * @Description 不为空命令校验器
 * @Date 2021-10-29 22:03
 * @Created by mango
 */
public class NotEmptyValidator extends CmdValidator {
    @Override
    public CmdResponse doValidate(CmdExecutor executor, JMedisClient client, String command) {
        if(!StringUtil.isNotBlank(command)) {
            return executor.returnError(ErrorEnum.EMPTY_CMD.getMsg());
        }
        return null;
    }
}
