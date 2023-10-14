package org.mango.jmedis.command.validator.impl;

import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.CmdExecutor;
import org.mango.jmedis.command.ICmd;
import org.mango.jmedis.command.validator.CmdValidator;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.response.CmdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 未知命令校验器
 * @Date 2021-10-29 22:03
 * @Created by mango
 */
public class UnknownValidator extends CmdValidator {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public CmdResponse doValidate(CmdExecutor executor, JMedisClient client, String command) {
        String[] arr = command.split(JMedisConstant.SPACE);
        String cmdType = arr[0];
        ICmd cmd = executor.getCmd(cmdType.toUpperCase());
        if (null == cmd) {
            log.warn("command[{}] not support!", cmdType);
            return executor.returnUnknown(cmdType);
        }
        return null;
    }
}
