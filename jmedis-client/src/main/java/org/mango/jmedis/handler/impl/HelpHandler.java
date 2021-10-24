package org.mango.jmedis.handler.impl;

import org.apache.commons.cli.CommandLine;
import org.mango.jmedis.client.ClientCommand;
import org.mango.jmedis.handler.Handler;
import org.mango.jmedis.util.ClientUtil;

/**
 * @Description help处理器
 * @Date 2021-10-24 15:24
 * @Created by mango
 */
public class HelpHandler extends Handler {

    @Override
    public boolean fit(CommandLine cmdLine) {
        return cmdLine.hasOption("help");
    }

    @Override
    public void doHandle(CommandLine cmdLine) {
        ClientUtil.showUsage(ClientCommand.options);
    }
}
