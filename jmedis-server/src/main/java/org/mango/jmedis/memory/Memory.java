package org.mango.jmedis.memory;

import org.mango.jmedis.config.ServerConf;
import org.mango.jmedis.memory.local.IType;
import org.mango.jmedis.memory.local.datatype.SDS;
import org.mango.jmedis.memory.local.JMedisString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Description 内存区
 * @Date 2021-10-23 14:49
 * @Created by mango
 */
public class Memory {
    private static Logger log = LoggerFactory.getLogger(Memory.class);
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
     * 查询数据
     * @param index 数据库下标
     * @param key key
     * @return itype数据
     */
    public static IType get(int index, String key) {
        return memory.get(index).get(key);
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
     * 获取下标index对应数据库的所有设置了过期时间的键值
     * @param index 数据库下标
     * @return
     */
    public static Set<String> getAllExpireKeys(int index) {
        return memory.get(index).hasExpireKeys();
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

    /**
     * 设置key的过期时间
     * @param dbIndex 数据库下标
     * @param key key
     * @param value 过期时间，单位秒
     * @return 设置成功，返回1；失败则返回-1
     */
    public static Integer expireKey(int dbIndex,String key, Integer value) {
        IType type = memory.get(dbIndex).get(key);
        if(Objects.isNull(type)){
            return -1;
        }else {
            type.setExpireTime(value);
            return 1;
        }
    }

    /**
     * 处理key的过期时间
     * @param dbIndex 数据库下标
     * @param key key
     */
    public static void handleExpireKey(int dbIndex,String key){
        IType type = memory.get(dbIndex).get(key);
        // 超时时间小于当前时间，则说明过期
        if(Objects.nonNull(type)
                && Objects.nonNull(type.getExpireTime())
                && type.getExpireTime().before(new Date())){
            Memory.removeKey(dbIndex,key);
        }
    }

    /**
     * 获取key的有效时间，单位秒
     * @param dbIndex 数据库下标
     * @param key key
     * @return 有效时间，秒;不存在则返回-1,过期返回0
     */
    public static Integer ttlKey(int dbIndex, String key) {
        IType type = memory.get(dbIndex).get(key);
        if(Objects.isNull(type) || Objects.isNull(type.getExpireTime())){
            return -1;
        }else {
            Date expireTime = type.getExpireTime();
            if(expireTime.after(new Date())){
                return (int)((expireTime.getTime() - System.currentTimeMillis()) / 1000);
            }else{
                return 0;
            }
        }
    }

    /**
     * 删除key
     * @param dbIndex 数据库下标
     * @param key key
     */
    public static void removeKey(int dbIndex, String key) {
        memory.get(dbIndex).remove(key);
        log.info("DB[{}} key[{}] has expire and removed",dbIndex,key);
    }
}
