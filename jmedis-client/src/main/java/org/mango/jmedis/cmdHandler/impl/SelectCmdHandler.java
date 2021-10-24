package org.mango.jmedis.cmdHandler.impl;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.cmdHandler.CmdHandler;

/**
 * @Description select 命令处理器
 * @Date 2021-10-24 17:14
 * @Created by mango
 */
public class SelectCmdHandler extends CmdHandler {

    @Override
    public boolean fit(JMedisClient client,String cmd) {
        String[] cmds = cmd.split(" ");
        if(cmds.length > 0 && "select".equals(cmds[0].toLowerCase())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void doHandle(JMedisClient client,String cmd) {
        String[] cmds = cmd.split(" ");
        client.setDbIndex(Integer.parseInt(cmds[1]));
    }
}
