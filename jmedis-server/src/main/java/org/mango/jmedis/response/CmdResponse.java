package org.mango.jmedis.response;

import lombok.Data;

/**
 * @Description 命令响应
 * @Date 2021-10-23 10:13
 * @Created by mango
 */
@Data
public class CmdResponse<T> {
    /**
     * 类型，如integer,error,空等
     */
    private String type;
    /**
     * 结果数据
     */
    private T result;

    public CmdResponse(){

    }

    @Override
    public String toString() {
        return "CmdResponse{" +
                "type='" + type + '\'' +
                ", result=" + result +
                '}';
    }
}
