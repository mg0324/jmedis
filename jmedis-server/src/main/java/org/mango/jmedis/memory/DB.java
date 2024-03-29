package org.mango.jmedis.memory;

import org.mango.jmedis.memory.local.IType;

import java.util.*;

/**
 * @Description 数据库内存
 * @Date 2021-10-23 14:50
 * @Created by mango
 */
public class DB {

    private Map<String, IType> dbMap;

    public DB(){
        dbMap = new HashMap<>();
    }

    /**
     * 存储数据到dbMap中
     * @param key key
     * @param typeImpl 数据
     */
    public void put(String key,IType typeImpl){
        dbMap.put(key,typeImpl);
    }

    /**
     * 通过key获取数据
     * @param key key
     * @return
     */
    public IType get(String key) {
        return dbMap.get(key);
    }

    /**
     * 获取数据库的keys
     * @return
     */
    public Set<String> keys() {
        return dbMap.keySet();
    }
    /**
     * 获取数据库的有过期时间的keys
     * @return
     */
    public Set<String> hasExpireKeys() {
        Set<String> keys = new HashSet<>();
        dbMap.keySet().parallelStream().forEach(e->{
            if(Objects.nonNull(dbMap.get(e).getExpireTime())){
                keys.add(e);
            }
        });
        return keys;
    }

    /**
     * 清除map
     */
    public void clear() {
        dbMap.clear();
    }

    /**
     * 删除key
     * @param key key
     */
    public void remove(String key) {
        dbMap.remove(key);
    }
}
