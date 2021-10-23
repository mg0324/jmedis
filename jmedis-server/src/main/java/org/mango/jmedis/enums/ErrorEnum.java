package org.mango.jmedis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 异常枚举
 * @Date 2021-10-23 11:12
 * @Created by mango
 */
@AllArgsConstructor
@Getter
public enum ErrorEnum {

    // 语法错误syntax
    UNKNOWN_CMD("1000","ERR unknown command"),
    // 参数个数错误
    PARAM_WRONG_NUMBER("2000","ERR param number wrong"),
    PARAM_WRONG_TYPE("2001","ERR param type wrong"),
    // 语法错误syntax
    SYNTA_WRONG("3000","ERR syntax error"),

    ;

    /**
     * 异常编码
     */
    String code;
    /**
     * 异常提示
     */
    String msg;


}
