package org.mango.jmedis.util;

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

}
