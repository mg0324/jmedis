package org.mango.jmedis.command;

import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.after.AfterHandler;
import org.mango.jmedis.command.after.impl.ExpireAfterHandler;
import org.mango.jmedis.command.validator.CmdValidator;
import org.mango.jmedis.command.validator.impl.AuthValidator;
import org.mango.jmedis.command.validator.impl.NotEmptyValidator;
import org.mango.jmedis.command.validator.impl.UnknownValidator;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.ClassUtil;
import org.mango.jmedis.util.ExecutorUtil;
import org.mango.jmedis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    // 命令校验器
    private CmdValidator cmdValidator;
    // 后置处理器
    private AfterHandler afterHandler;


    public CmdExecutor(){
        cmdMap = new HashMap<>();
        // 注册命令执行器
        this.registerCmdMap();
        // 初始化命令校验器
        this.initCmdValidator();
    }

    // 初始化命令校验器
    private void initCmdValidator() {
        // 1.校验非空命令
        cmdValidator = new NotEmptyValidator();
        // 2.校验未知命令
        UnknownValidator unknownValidator = new UnknownValidator();
        cmdValidator.setNext(unknownValidator);
        // 3.校验权限
        AuthValidator authValidator = new AuthValidator();
        unknownValidator.setNext(authValidator);
    }

    // 通过注解@Cmd注册命令
    private void registerCmdMap() {
        try {
            List<Class<?>> cmdClassList = ClassUtil.getClasses(this.getClass().getPackage().getName());
            List<String> cmdList = new ArrayList<>();
            for(Class e : cmdClassList){
                Cmd cmd = (Cmd) e.getAnnotation(Cmd.class);
                if(Objects.nonNull(cmd)) {
                    ICmd bean = (ICmd) e.newInstance();
                    String command = StringUtil.getNoCmdClassName(e.getSimpleName());
                    cmdMap.put(command, bean);
                    cmdList.add(e.getSimpleName());
                }
            }
            log.info("register client {} success",cmdList);
        }catch (Exception e){
            log.error("init client map error:{}",e.getMessage(),e);
        }
    }

    /**
     * 通过命令字符串获取对应命令对象
     * @param command 命令字符串
     * @return Cmd
     */
    public ICmd getCmd(String command){
        return this.cmdMap.get(command);
    }

    /**
     * 执行命令
     * @param client 客户端
     * @param command 命令
     * @return 执行成功返回结果，否则返回null
     */
    public CmdResponse execute(JMedisClient client,String command){
        log.debug("execute command:{}",command);
        // 执行校验链
        CmdResponse response = cmdValidator.validate(this,client,command);
        if(Objects.nonNull(response)){
            return response;
        }
        // 分发命令并得到结果
        String[] param = oneStartArr(command);
        ICmd cmd = parseCmd(command);
        CmdResponse result = cmd.dispatch(client,param);
        // 设置并执行后置处理器
        this.setAndExecuteAfterHandler(cmd,client,param);
        return result;
    }

    /**
     * 设置并执行后置处理器
     * @param cmd 命令
     * @param client 客户端
     * @param param 参数
     */
    private void setAndExecuteAfterHandler(ICmd cmd, JMedisClient client, String[] param) {
        // 初始化后置处理器
        afterHandler = new ExpireAfterHandler(cmd,client,param);
        // 后置处理器执行,交由线程池异步执行
        ExecutorUtil.execute(afterHandler);
    }

    /**
     * 取数据从1开始的数据到新数组
     * @param command
     * @return
     */
    private String[] oneStartArr(String command){
        String[] arr = command.split(JMedisConstant.SPACE);
        String[] result = new String[arr.length-1];
        for(int i=1;i<arr.length;i++){
            result[i-1] = arr[i];
        }
        return result;
    }

    /**
     * 通过原始命令字符串得到命令对象
     * @param command
     * @return
     */
    private ICmd parseCmd(String command){
        String[] arr = command.split(JMedisConstant.SPACE);
        String cmdType = arr[0];
        return cmdMap.get(cmdType.toUpperCase());
    }

    /**
     * 返回未知命令
     * @return
     */
    public CmdResponse<String> returnUnknown(String cmd){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_ERROR);
        String msg = ErrorEnum.UNKNOWN_CMD.getMsg()
                + " `"+cmd+"`";
        response.setResult(msg);
        return response;
    }

    /**
     * 返回错误信息
     * @return
     */
    public CmdResponse<String> returnError(String msg){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_ERROR);
        response.setResult(msg);
        return response;
    }
}
