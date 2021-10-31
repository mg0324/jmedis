package org.mango.jmedis.memory;

import org.mango.jmedis.datatype.IType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 清除map
     */
    public void clear() {
        dbMap.clear();
    }
}
