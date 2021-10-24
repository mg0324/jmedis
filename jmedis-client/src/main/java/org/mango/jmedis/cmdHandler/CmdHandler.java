package org.mango.jmedis.cmdHandler;

import org.mango.jmedis.client.JMedisClient;

/**
 * @Description 命令处理器
 * @Date 2021-10-24 17:12
 * @Created by mango
 */
public abstract class CmdHandler {
    // 下一个处理器
    private CmdHandler next;
    /**
     * 链式处理命令行
     *
     * @param cmd 命令行
     */
    public void handle(JMedisClient client,String cmd){
        // 条件满足，自己处理
        if(fit(client,cmd)){
            this.doHandle(client,cmd);
        }else{
            //交给下一个处理器处理
            if(this.next != null) {
                this.next.handle(client, cmd);
            }
        }
    }

    /**
     * 设置下一个处理器
     * @param handler
     */
    public void setNext(CmdHandler handler){
        this.next = handler;
    }

    /**
     * 判断该命令是否适合自己处理
     * @param client 客户端
     * @param cmd 命令行
     * @return
     */
    public abstract boolean fit(JMedisClient client,String cmd);

    /**
     * 处理命令
     * @param client 客户端
     * @param cmd 命令行
     * @return
     */
    public abstract void doHandle(JMedisClient client,String cmd);
}
