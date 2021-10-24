package org.mango.jmedis.handler;

import org.apache.commons.cli.CommandLine;

/**
 * @Description 处理器
 * @Date 2021-10-24 15:17
 * @Created by mango
 */
public abstract class Handler {
    // 下一个处理器
    private Handler next;
    /**
     * 链式处理命令行
     * @param cmdLine 命令行
     */
    public void handle(CommandLine cmdLine){
        // 条件满足，自己处理
        if(fit(cmdLine)){
            this.doHandle(cmdLine);
        }else{
            //交给下一个处理器处理
            if(null != this.next) {
                this.next.handle(cmdLine);
            }
        }
    }

    /**
     * 设置下一个处理器
     * @param handler
     */
    public void setNext(Handler handler){
        this.next = handler;
    }

    /**
     * 判断该命令是否适合自己处理
     * @param cmdLine 命令行
     * @return
     */
    public abstract boolean fit(CommandLine cmdLine);

    /**
     * 处理命令
     * @param cmdLine 命令行
     * @return
     */
    public abstract void doHandle(CommandLine cmdLine);
}
