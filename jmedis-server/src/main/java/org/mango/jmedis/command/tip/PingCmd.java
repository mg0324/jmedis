package org.mango.jmedis.command.tip;

import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.command.ICmd;
import org.mango.jmedis.constant.JMedisConstant;

/**
 * @Description ping cmd实现
 * @Date 2021-10-22 23:22
 * @Created by mango
 */
public class PingCmd extends BaseCmd implements ICmd {
    @Override
    public String execute(String[] param) {
        return "PONG" + JMedisConstant.BR;
    }
}
