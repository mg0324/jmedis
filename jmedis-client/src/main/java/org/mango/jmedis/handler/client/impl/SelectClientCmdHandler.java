package org.mango.jmedis.handler.client.impl;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.handler.client.ClientCmdHandler;
import org.mango.jmedis.constant.JMedisConstant;

/**
 * @Description select 命令处理器
 * @Date 2021-10-24 17:14
 * @Created by mango
 */
public class SelectClientCmdHandler extends ClientCmdHandler {

    @Override
    public boolean fit(JMedisClient client,String cmd) {
        String[] cmds = cmd.split(JMedisConstant.SPACE);
        if(cmds.length > 0 && JMedisConstant.SELECT.equals(cmds[0].toLowerCase())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void doHandle(JMedisClient client,String cmd) {
        String[] cmds = cmd.split(JMedisConstant.SPACE);
        client.setDbIndex(Integer.parseInt(cmds[1]));
    }
}
