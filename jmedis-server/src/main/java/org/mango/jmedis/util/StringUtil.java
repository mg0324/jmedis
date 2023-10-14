package org.mango.jmedis.util;

import org.mango.jmedis.constant.JMedisConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 将时间转换为字符串格式
     * @param datetime 时间
     * @return yyyy-MM-dd HH:mm:ss格式字符串
     */
    public static String datetime2string(Date datetime){
        SimpleDateFormat sdf = new SimpleDateFormat(JMedisConstant.PATTERN_DATETIME);
        return sdf.format(datetime);
    }

    /**
     * 将日期转换为字符串格式
     * @param date 时间
     * @return yyyy-MM-dd格式字符串
     */
    public static String date2string(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(JMedisConstant.PATTERN_DATE);
        return sdf.format(date);
    }


}
