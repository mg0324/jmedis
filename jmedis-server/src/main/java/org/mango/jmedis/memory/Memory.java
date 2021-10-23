package org.mango.jmedis.memory;

import org.mango.jmedis.config.ServerConf;
import org.mango.jmedis.constant.DataTypeConstant;
import org.mango.jmedis.datatype.SDS;
import org.mango.jmedis.memory.local.JMedisString;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 内存区
 * @Date 2021-10-23 14:49
 * @Created by mango
 */
public class Memory {
    private static Map<Integer,DB> memory;

    // 初始化数据库内存
    static {
        memory = new HashMap<>(ServerConf.DB_SIZE);
        for(int i=0;i<ServerConf.DB_SIZE;i++){
            memory.put(i,new DB());
        }
    }

    private Memory(){}

    /**
     * 存储string数据
     * @param index 数据库下标
     * @param key 键
     * @param value 值
     */
    public static void storeString(int index,SDS key,SDS value){
        JMedisString str = new JMedisString(key,value);
        memory.get(index).put(DataTypeConstant.STRING,str);
    }

    /**
     * 查询string数据
     * @param index 数据库下标
     * @param keySds key
     * @return valueSds
     */
    public static SDS getString(int index, SDS keySds) {
        return memory.get(index).get(DataTypeConstant.STRING,keySds);
    }
}
