package org.mango.jmedis.command;

import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.StringUtil;

/**
 * @Description 基础命令
 * @Date 2021-10-22 23:23
 * @Created by mango
 */
public abstract class BaseCmd<T> implements ICmd<T> {

    /**
     * 返回参数个数不正确
     * @return
     */
    protected CmdResponse<String> errorWrongNumber(){
        //for 'keys' command
        String msg = ErrorEnum.PARAM_WRONG_NUMBER.getMsg()
                + "for '"+this.name().toLowerCase()+"' command";
        return this.renderUseError(msg);
    }

    /**
     * 以错误类型返回数据
     * @param data 数据
     * @return
     */
    protected CmdResponse<String> renderUseError(String data){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_ERROR);
        response.setResult(StringUtil.wrapBr(data));
        return response;
    }

    /**
     * 以空类型返回数据
     * @param data 数据
     * @return
     */
    protected CmdResponse<String> renderUseEmpty(String data){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_EMPTY);
        response.setResult(StringUtil.wrapBr(data));
        return response;
    }

    /**
     * 以字符串类型返回数据
     * @param data 数据
     * @return
     */
    protected CmdResponse<String> renderUseString(String data){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_STRING);
        response.setResult(StringUtil.wrapBr(data));
        return response;
    }

    /**
     * 以OK返回数据
     * @return
     */
    protected CmdResponse<String> renderOk(){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_EMPTY);
        response.setResult(StringUtil.wrapBr("OK"));
        return response;
    }

    /**
     * 以数组类型返回数据
     * @param data 数据
     * @return
     */
    protected CmdResponse<Integer> renderUseInteger(String data){
        CmdResponse<Integer> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_INTEGER);
        response.setResult(Integer.parseInt(data.trim()));
        return response;
    }

    /**
     * 以null类型返回数据
     * @return
     */
    protected CmdResponse<String> renderUseNull(){
        CmdResponse<String> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_NULL);
        response.setResult(StringUtil.wrapBr(""));
        return response;
    }




}
