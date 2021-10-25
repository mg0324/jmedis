package org.mango.jmedis.util;

import org.mango.jmedis.constant.JMedisConstant;

/**
 * @Description 字符串工具
 * @Date 2021-10-22 23:29
 * @Created by mango
 */
public class StringUtil {
    /**
     * 判断字符串不为空
     * @param value
     * @return
     */
    public static boolean isNotBlank(String value){
        if(null != value && value.trim().length()>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 加上换行符
     * @param data 数据
     * @return
     */
    public static String wrapBr(String data){
        return data + JMedisConstant.BR;
    }

    /**
     * 去掉类名后面的Cmd
     * @param cmdClassName 类名
     * @return
     */
    public static String getNoCmdClassName(String cmdClassName){
        return cmdClassName.toUpperCase().replaceAll("CMD","");
    }
}
