package org.mango.jmedis.handler.cmd.impl;

import org.apache.commons.cli.CommandLine;
import org.mango.jmedis.config.ClientConf;
import org.mango.jmedis.handler.cmd.CmdHandler;
import org.mango.jmedis.util.ClientUtil;

/**
 * @Description main处理器
 * @Date 2021-10-24 15:24
 * @Created by mango
 */
public class MainCmdHandler extends CmdHandler {

    @Override
    public boolean fit(CommandLine cmdLine) {
        return true;
    }

    @Override
    public boolean doHandle(CommandLine cmdLine) {
        String host = cmdLine.getOptionValue("h",ClientConf.DEFAULT_HOST);
        String port = cmdLine.getOptionValue("p",ClientConf.DEFAULT_PORT+"");
        try {
            int nPort = Integer.parseInt(port);
            ClientUtil.buildClient(host,nPort);
        }catch (NumberFormatException e){
            ClientUtil.println("(error) port need number type");
        }
        return false;
    }
}
