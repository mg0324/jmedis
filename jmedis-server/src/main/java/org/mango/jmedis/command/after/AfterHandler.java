package org.mango.jmedis.command.after;

import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.ICmd;

import java.util.Objects;

/**
 * @Description 后置处理器
 * @Date 2021-11-02 21:56
 * @Created by mango
 */
public abstract class AfterHandler implements Runnable {
    private AfterHandler next;
    private JMedisClient client;
    private String[] param;
    private ICmd cmd;

    public AfterHandler(ICmd cmd, JMedisClient client,String[] param){
        this.cmd = cmd;
        this.client = client;
        this.param = param;
    }

    @Override
    public void run() {
        this.handle(cmd,client,param);
    }

    /**
     * 执行业务逻辑
     * @param cmd 命令对象
     * @param client 客户端
     * @param param 命令参数
     */
    public void handle(ICmd cmd, JMedisClient client,String[] param){
        // 启用
        if(this.enable(cmd,client,param)){
            boolean result = this.doHandle(cmd,client,param);
            if(result && Objects.nonNull(next)){
                this.handle(cmd,client,param);
            }
        }else{
            // 未启用则执行下一个
            if(Objects.nonNull(next)){
                this.handle(cmd,client,param);
            }
        }
    }

    /**
     * 设置下一个
     * @param next
     */
    public void setNext(AfterHandler next) {
        this.next = next;
    }

    /**
     * 是否启用后置处理器
     * @param cmd 命令对象
     * @param client 客户端
     * @param param 命令参数
     * @return 启用返回true
     */
    public abstract boolean enable(ICmd cmd, JMedisClient client,String[] param);

    /**
     * 后置处理器逻辑实现
     * @param cmd 命令对象
     * @param client 客户端
     * @param param 命令参数
     * @return 是否继续执行，true继续执行
     */
    public abstract boolean doHandle(ICmd cmd, JMedisClient client,String[] param);
}
