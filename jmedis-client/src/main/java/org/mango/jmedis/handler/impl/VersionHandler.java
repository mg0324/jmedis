package org.mango.jmedis.handler.impl;

import org.apache.commons.cli.CommandLine;
import org.mango.jmedis.config.ClientConf;
import org.mango.jmedis.handler.Handler;
import org.mango.jmedis.util.ClientUtil;

/**
 * @Description version处理器
 * @Date 2021-10-24 15:24
 * @Created by mango
 */
public class VersionHandler extends Handler {

    @Override
    public boolean fit(CommandLine cmdLine) {
        return cmdLine.hasOption("version");
    }

    @Override
    public void doHandle(CommandLine cmdLine) {
        ClientUtil.println(ClientConf.PROGRAM_NAME + " " + ClientConf.VERSION);
    }
}
