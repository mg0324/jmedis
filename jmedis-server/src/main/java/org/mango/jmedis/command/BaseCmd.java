package org.mango.jmedis.command;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.enums.ErrorEnum;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.util.StringUtil;

import java.util.List;

/**
 * @Description 基础命令
 * @Date 2021-10-22 23:23
 * @Created by mango
 */
public abstract class BaseCmd<T> implements ICmd<T> {

    // 模板方法
    @Override
    public CmdResponse<T> dispatch(JMedisClient client, String[] param) {
        // 先校验参数个数
        if(expect(param)){
            // 再校验参数数据
            CmdResponse<T> validateResponse = validate(client,param);
            if(validateResponse == null) {
                return this.execute(client, param);
            }else{
                return validateResponse;
            }
        }else{
            //错误的参数个数
            return (CmdResponse<T>) this.errorWrongNumber();
        }
    }

    @Override
    public CmdResponse<T> validate(JMedisClient client, String[] param) {
        // 默认不校验参数数据，设置为通过
        return null;
    }

    /**
     * 参数格式相等判断
     * @param param 参数
     * @param size 判断值
     * @return 相等则true
     */
    protected boolean sizeEq(String[] param,int size){
        return param.length == size ? true : false;
    }

    /**
     * 参数格式小于等于判断
     * @param param 参数
     * @param size 判断值
     * @return
     */
    protected boolean sizeLe(String[] param,int size){
        return param.length <= size ? true : false;
    }
    /**
     * 返回参数个数不正确
     * @return
     */
    protected CmdResponse<String> errorWrongNumber(){
        //for 'keys' command
        String msg = ErrorEnum.PARAM_WRONG_NUMBER.getMsg()
                + " for '"+this.name().toLowerCase()+"' command";
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
     * 以list类型返回数据
     * @param list 数据
     * @return
     */
    protected CmdResponse<List> renderUseList(List<String> list){
        CmdResponse<List> response = new CmdResponse<>();
        response.setType(JMedisConstant.RESPONSE_LIST);
        response.setResult(list);
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
