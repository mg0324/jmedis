package org.mango.jmedis.handler.cmd.impl;

import org.apache.commons.cli.CommandLine;
import org.mango.jmedis.handler.cmd.CmdHandler;
import org.mango.jmedis.util.ClientUtil;

/**
 * @Description password 处理器
 * @Date 2021-10-27 15:24
 * @Created by mango
 */
public class PasswordCmdHandler extends CmdHandler {

    @Override
    public boolean fit(CommandLine cmdLine) {
        return cmdLine.hasOption("password");
    }

    @Override
    public boolean doHandle(CommandLine cmdLine) {
        ClientUtil.getClient().setAuthPwd(cmdLine.getOptionValue("password"));
        return true;//继续执行主命令处理器

    }
}
