package org.mango.jmedis.command;

import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.string.GetCmd;
import org.mango.jmedis.command.string.SetCmd;
import org.mango.jmedis.command.tip.PingCmd;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 命令执行器
 * @Date 2021-10-23 10:59
 * @Created by mango
 */
@Slf4j
public class CmdExecutor {

    // 命令接口注册map
    private Map<String, ICmd> cmdMap;

    public CmdExecutor(){
        cmdMap = new HashMap<>();
        // 注册命令执行器
        cmdMap.put(JMedisConstant.CMD_PING, new PingCmd());
        cmdMap.put(JMedisConstant.CMD_SET, new SetCmd());
        cmdMap.put(JMedisConstant.CMD_GET, new GetCmd());
    }

    /**
     * 执行命令
     * @param client 客户端
     * @param command 命令
     * @return 执行成功返回结果，否则返回null
     */
    public CmdResponse execute(JMedisClient client,String command){
        log.debug("execute command:{}",command);
        if(StringUtil.isNotBlank(command)) {
            String[] arr = command.split(" ");
            String cmdType = arr[0];
            ICmd cmd = cmdMap.get(cmdType.toUpperCase());
            if (null == cmd) {
                log.warn("command[{}] not support!", cmdType);
            } else {
                // 执行并得到结果
                return cmd.execute(client.getDbIndex(),oneStartArr(arr));
            }
        }
        return null;
    }

    /**
     * 取数据从1开始的数据到新数组
     * @param arr
     * @return
     */
    private String[] oneStartArr(String[] arr){
        String[] result = new String[arr.length-1];
        for(int i=1;i<arr.length;i++){
            result[i-1] = arr[i];
        }
        return result;
    }
}
