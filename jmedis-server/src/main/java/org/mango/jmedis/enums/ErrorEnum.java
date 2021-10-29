package org.mango.jmedis.enums;

/**
 * @Description 异常枚举
 * @Date 2021-10-23 11:12
 * @Created by mango
 */
public enum ErrorEnum {

    // 语法错误syntax
    UNKNOWN_CMD("1000", "ERR unknown command"),
    EMPTY_CMD("1001", "ERR empty command"),
    // 参数个数错误
    PARAM_WRONG_NUMBER("2000", "ERR wrong number of arguments"),
    PARAM_WRONG_TYPE("2001", "ERR param type wrong"),
    PARAM_WRONG_RANGE("2002", "ERR param range wrong"),
    // 语法错误syntax
    SYNTA_WRONG("3000", "ERR syntax error"),
    // 认证失败
    AUTH_WRONG_NEED("4000", "NOAUTH Authentication required"),
    AUTH_WRONG_PASS("4001", "WRONGPASS invalid password"),

    ;

    /**
     * 异常编码
     */
    String code;
    /**
     * 异常提示
     */
    String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }}
