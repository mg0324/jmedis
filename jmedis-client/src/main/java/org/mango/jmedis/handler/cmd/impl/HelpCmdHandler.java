package org.mango.jmedis.handler.cmd.impl;

import org.apache.commons.cli.CommandLine;
import org.mango.jmedis.client.ClientCommand;
import org.mango.jmedis.handler.cmd.CmdHandler;
import org.mango.jmedis.util.ClientUtil;

/**
 * @Description help处理器
 * @Date 2021-10-24 15:24
 * @Created by mango
 */
public class HelpCmdHandler extends CmdHandler {

    @Override
    public boolean fit(CommandLine cmdLine) {
        return cmdLine.hasOption("help");
    }

    @Override
    public boolean doHandle(CommandLine cmdLine) {
        ClientUtil.showUsage(ClientCommand.options);
        return false;
    }
}
