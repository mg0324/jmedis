package org.mango.jmedis.memory.local;

import lombok.Getter;
import org.mango.jmedis.datatype.IType;
import org.mango.jmedis.datatype.SDS;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description String数据类型
 * @Date 2021-10-23 15:14
 * @Created by mango
 */
@Getter
public class JMedisString implements IType {

    private static Map<String,JMedisString> strMap = new HashMap<>();

    private SDS key;
    private SDS value;

    public JMedisString(SDS key,SDS value){
        this.key = key;
        this.value = value;
        strMap.put(key.getString(),this);
    }

    /**
     * 通过key获取value
     * @param key
     * @return
     */
    public SDS getValue(SDS key){
        JMedisString str = strMap.get(key.getString());
        if(null == str){
            return null;
        }
        return str.getValue();
    }
}
