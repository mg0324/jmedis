package org.mango.jmedis.command.impl.server;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.memory.Memory;
import org.mango.jmedis.response.CmdResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description keys 命令
 * @Date 2021-10-24 22:54
 * @Created by mango
 */
@Cmd
public class KeysCmd extends BaseCmd<List> {
    @Override
    public CmdResponse<List> execute(JMedisClient client, String[] param) {
        // 先查询当前数据库里的键值
        Set<String> keys = Memory.getAllKeys(client.getDbIndex());
        String findKey = param[0];
        List<String> list = new ArrayList<>();
        if(findKey.equals("*")){
            list.addAll(keys);
        }else{
            // 利用字符串的正则匹配
            list = keys.parallelStream().filter(e->e.matches(findKey)).collect(Collectors.toList());
        }
        return this.renderUseList(list);
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,1);
    }

}
