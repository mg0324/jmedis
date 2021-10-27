package org.mango.jmedis.command;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.ClassUtil;
import org.mango.jmedis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Description 命令执行器
 * @Date 2021-10-23 10:59
 * @Created by mango
 */
public class CmdExecutor {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    // 命令接口注册map
    private Map<String, ICmd> cmdMap;

    public CmdExecutor(){
        cmdMap = new HashMap<>();
        // 注册命令执行器
        this.registerCmdMap();
    }

    // 通过注解@Cmd注册命令
    private void registerCmdMap() {
        try {
            List<Class<?>> cmdClassList = ClassUtil.getClasses(this.getClass().getPackage().getName());
            List<String> cmdList = new ArrayList<>();
            for(Class e : cmdClassList){
                Annotation[] annos = e.getAnnotationsByType(Cmd.class);
                if(annos.length>0) {
                    ICmd bean = (ICmd) e.newInstance();
                    String cmd = StringUtil.getNoCmdClassName(e.getSimpleName());
                    cmdMap.put(cmd, bean);
                    cmdList.add(e.getSimpleName());
                }
            }
            log.info("register client {} success",cmdList);
        }catch (Exception e){
            log.error("init client map error:{}",e.getMessage(),e);
        }
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
                return returnUnknown(cmdType);
            } else {
                // 先判断是否通过认证 或者是 认证命令才能分发执行
                if(client.isPassAuth()
                        || cmdType.toUpperCase().equals(JMedisConstant.CMD_AUTH)
                        || cmdType.toUpperCase().equals(JMedisConstant.CMD_QUIT)) {
                    // 分发命令并得到结果
                    return cmd.dispatch(client, oneStartArr(arr));
                }else{
                    return returnError(ErrorEnum.AUTH_WRONG_NEED.getMsg());
                }
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

    /**
     * 返回未知命令
     * @return
     */
    private CmdResponse<String> returnUnknown(String cmd){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_ERROR);
        String msg = ErrorEnum.UNKNOWN_CMD.getMsg()
                + " `"+cmd+"`";
        response.setResult(StringUtil.wrapBr(msg));
        return response;
    }

    /**
     * 返回错误信息
     * @return
     */
    private CmdResponse<String> returnError(String msg){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_ERROR);
        response.setResult(StringUtil.wrapBr(msg));
        return response;
    }
}
