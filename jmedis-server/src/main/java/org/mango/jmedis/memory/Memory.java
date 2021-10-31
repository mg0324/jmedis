package org.mango.jmedis.memory;

import org.mango.jmedis.config.ServerConf;
import org.mango.jmedis.datatype.SDS;
import org.mango.jmedis.memory.local.JMedisString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description 内存区
 * @Date 2021-10-23 14:49
 * @Created by mango
 */
public class Memory {
    private static Map<Integer,DB> memory;

    // 初始化数据库内存
    static {
        memory = new HashMap<>(ServerConf.getConf().getDbSize());
        for(int i=0;i<ServerConf.getConf().getDbSize();i++){
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
        memory.get(index).put(key.getString(),str);
    }

    /**
     * 查询string数据
     * @param index 数据库下标
     * @param key key
     * @return valueSds
     */
    public static SDS getString(int index, String key) {
        JMedisString str = (JMedisString) memory.get(index).get(key);
        if(str == null){
            return null;
        }
        return str.getValue();
    }

    /**
     * 获取下标index对应数据库的所有键值
     * @param index 数据库下标
     * @return
     */
    public static Set<String> getAllKeys(int index){
        return memory.get(index).keys();
    }

    /**
     * 清除数据库下标里的keys
     * @param dbIndex 数据库下标
     */
    public static void clearAllKeys(int dbIndex) {
        memory.get(dbIndex).clear();
    }

    /**
     * 清除所有数据库里的keys
     */
    public static void clearAllKeys() {
        for(int i=0;i<ServerConf.getConf().getDbSize();i++){
            clearAllKeys(i);
        }
    }

    /**
     * 追加字符串
     * @param dbIndex 数据库下标
     * @param keySds key
     * @param valueSds value
     * @return 字符值长度
     */
    public static Integer appendString(int dbIndex, SDS keySds, SDS valueSds) {
        JMedisString str = (JMedisString) memory.get(dbIndex).get(keySds.getString());
        str.getValue().setValue(str.getValue().getString()+valueSds.getString());
        return str.getValue().getBytes().length;
    }
}
