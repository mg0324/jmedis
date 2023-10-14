package org.mango.jmedis.command.validator.impl;

import org.mango.jmedis.annotation.NoAuth;
import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.CmdExecutor;
import org.mango.jmedis.command.ICmd;
import org.mango.jmedis.command.validator.CmdValidator;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;

import java.util.Objects;

/**
 * @Description 权限校验器
 * @Date 2021-10-29 22:16
 * @Created by mango
 */
public class AuthValidator extends CmdValidator {

    @Override
    public CmdResponse doValidate(CmdExecutor executor, JMedisClient client, String command) {
        String[] arr = command.split(JMedisConstant.SPACE);
        String cmdType = arr[0];
        ICmd cmd = executor.getCmd(cmdType.toUpperCase());
        // 读取不需要权限的命令跳过
        NoAuth noAuth = cmd.getClass().getAnnotation(NoAuth.class);
        if(Objects.nonNull(noAuth)){
            // 不需要认证
            return null;
        }
        // 需要认证
        if(client.isPassAuth()){// 已经认证通过
            return null;
        }else{
            // 返回需要认证的提示
            return executor.returnError(ErrorEnum.AUTH_WRONG_NEED.getMsg());
        }
    }
}
