package org.mango.jmedis.handler.cmd;

import org.apache.commons.cli.CommandLine;

/**
 * @Description 命令行处理器
 * @Date 2021-10-24 15:17
 * @Created by mango
 */
public abstract class CmdHandler {
    // 下一个处理器
    private CmdHandler next;
    /**
     * 链式处理命令行
     * @param cmdLine 命令行
     */
    public void handle(CommandLine cmdLine){
        // 条件满足，自己处理
        boolean doNext = false;
        if(fit(cmdLine) && this.doHandle(cmdLine)){
            doNext = true;
        }else{
            doNext = true;
        }
        //交给下一个处理器处理
        if(doNext && null != this.next){
            this.next.handle(cmdLine);
        }
    }

    /**
     * 设置下一个处理器
     * @param cmdHandler
     */
    public void setNext(CmdHandler cmdHandler){
        this.next = cmdHandler;
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
     * @return 是否继续执行，true继续执行，false中断
     */
    public abstract boolean doHandle(CommandLine cmdLine);
}
