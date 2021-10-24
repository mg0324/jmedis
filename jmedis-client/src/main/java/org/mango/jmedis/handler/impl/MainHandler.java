package org.mango.jmedis.handler.impl;

import org.apache.commons.cli.CommandLine;
import org.mango.jmedis.client.ClientCommand;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.config.ClientConf;
import org.mango.jmedis.handler.Handler;
import org.mango.jmedis.util.ClientUtil;
import org.mango.jmedis.util.StringUtil;

/**
 * @Description main处理器
 * @Date 2021-10-24 15:24
 * @Created by mango
 */
public class MainHandler extends Handler {

    private JMedisClient client;

    @Override
    public boolean fit(CommandLine cmdLine) {
        return true;
    }

    @Override
    public void doHandle(CommandLine cmdLine) {
        String host = cmdLine.getOptionValue("h",ClientConf.DEFAULT_HOST);
        String port = cmdLine.getOptionValue("p",ClientConf.DEFAULT_PORT+"");
        try {
            int nPort = Integer.parseInt(port);
            client = new JMedisClient(host,nPort);
        }catch (NumberFormatException e){
            ClientUtil.println("(error) port need number type");
        }
    }
}
