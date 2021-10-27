package org.mango.jmedis.handler.cmd.impl;

import org.apache.commons.cli.CommandLine;
import org.mango.jmedis.config.ClientConf;
import org.mango.jmedis.handler.cmd.CmdHandler;
import org.mango.jmedis.util.ClientUtil;

/**
 * @Description version处理器
 * @Date 2021-10-24 15:24
 * @Created by mango
 */
public class VersionCmdHandler extends CmdHandler {

    @Override
    public boolean fit(CommandLine cmdLine) {
        return cmdLine.hasOption("version");
    }

    @Override
    public boolean doHandle(CommandLine cmdLine) {
        ClientUtil.println(ClientConf.PROGRAM_NAME + " " + ClientConf.VERSION);
        return false;
    }
}
