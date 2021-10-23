package org.mango.jmedis.memory;

import org.mango.jmedis.datatype.IType;
import org.mango.jmedis.datatype.SDS;
import org.mango.jmedis.memory.local.JMedisString;

import java.util.HashMap;
import java.util.Map;

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
     * @param dataType 数据类型
     * @param typeImpl 数据
     */
    public void put(String dataType,IType typeImpl){
        dbMap.put(dataType,typeImpl);
    }

    /**
     *
     * @param dataType
     * @param keySds
     * @return
     */
    public SDS get(String dataType, SDS keySds) {
        JMedisString str = (JMedisString) dbMap.get(dataType);
        if(null == str){
            return null;
        }
        return str.getValue(keySds);
    }
}
