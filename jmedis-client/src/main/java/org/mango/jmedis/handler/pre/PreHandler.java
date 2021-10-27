package org.mango.jmedis.handler.pre;

import java.io.IOException;

/**
 * @Description 前置处理器
 * @Date 2021-10-27 15:17
 * @Created by mango
 */
public abstract class PreHandler {

    /**
     * 执行方法
     */
    public abstract void doHandle() throws IOException;

    /**
     * 执行方法
     * @throws IOException
     */
    public void handle() throws IOException {
        if(enable()){
            this.doHandle();
        }
    }

    /**
     * 是否执行
     * @return
     */
    public abstract boolean enable();

}
