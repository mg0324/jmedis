package org.mango.jmedis.command.validator;


import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.CmdExecutor;
import org.mango.jmedis.response.CmdResponse;

import java.util.Objects;

/**
 * @Description 命令校验器
 * @Date 2021-10-29 15:17
 * @Created by mango
 */
public abstract class CmdValidator {
    // 下一个校验器
    private CmdValidator next;
    /**
     * 链式校验命令行
     * @param executor 执行器
     * @param client 客户端
     * @param command 命令
     * @return 校验结果，校验通过返回null，继续下一个校验，校验失败则返回提示对象
     */
    public CmdResponse validate(CmdExecutor executor, JMedisClient client, String command){
        // 条件满足，自己处理
        CmdResponse response = this.doValidate(executor,client,command);
        if(Objects.nonNull(response)){
            return response;
        }
        //交给下一个处理器处理
        if(null != this.next){
            return this.next.validate(executor,client,command);
        }
        return null;
    }

    /**
     * 设置下一个校验器
     * @param cmdValidator
     */
    public void setNext(CmdValidator cmdValidator){
        this.next = cmdValidator;
    }

    /**
     * 校验命令
     * @param executor 执行器
     * @param client 客户端
     * @param command 命令行
     * @return 校验结果，有返回则提前结束，无返回则执行下一个
     */
    public abstract CmdResponse doValidate(CmdExecutor executor, JMedisClient client,String command);
}
