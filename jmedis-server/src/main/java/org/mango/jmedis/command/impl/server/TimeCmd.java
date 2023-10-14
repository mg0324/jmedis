package org.mango.jmedis.command.impl.server;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.StringUtil;
import java.util.Date;

/**
 * 返回服务器时间
 * @Description time 命令
 * @Date 2021-10-30 09:28
 * @Created by mango
 */
@Cmd
public class TimeCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        return this.renderUseEmpty(StringUtil.datetime2string(new Date()));
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,0);
    }

}
