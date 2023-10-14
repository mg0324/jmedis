package org.mango.jmedis.command.impl.connection;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.annotation.NoAuth;
import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.core.config.ServerConf;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.StringUtil;

/**
 * @Description auth cmd实现
 * @Date 2021-10-26 23:15
 * @Created by mango
 */
@Cmd
@NoAuth
public class AuthCmd extends BaseCmd<String> {
    @Override
    public CmdResponse<String> execute(JMedisClient client, String[] param) {
        String password = param[0];
        if(StringUtil.isNotBlank(password) && password.equals(ServerConf.getConf().getAuthPassword())){
            client.setPassAuth(true);
            return this.renderOk();
        }else{
            return this.renderUseError(ErrorEnum.AUTH_WRONG_PASS.getMsg());
        }
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,1);
    }
}
