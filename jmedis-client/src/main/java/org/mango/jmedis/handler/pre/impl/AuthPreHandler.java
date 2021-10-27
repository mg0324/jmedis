package org.mango.jmedis.handler.pre.impl;

import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.handler.pre.PreHandler;
import org.mango.jmedis.util.ClientUtil;
import org.mango.jmedis.util.StringUtil;

import java.io.IOException;

/**
 * @Description 认证前置处理器
 * @Date 2021-10-27 21:09
 * @Created by mango
 */
public class AuthPreHandler extends PreHandler {

    @Override
    public void doHandle() throws IOException {
        // 发送认证命令给服务器
        String cmd = JMedisConstant.AUTH + JMedisConstant.SPACE + ClientUtil.getClient().getAuthPwd();
        ClientUtil.getClient().executeCmd(cmd);
        // 阻塞读取数据
        if(!ClientUtil.getClient().readData()){
            // 认证失败，退出
            ClientUtil.getClient().close();
        }
    }

    @Override
    public boolean enable() {
        // 认证密码不为空
        return StringUtil.isNotBlank(ClientUtil.getClient().getAuthPwd());
    }
}
