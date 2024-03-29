package org.mango.jmedis.memory.local;

import org.mango.jmedis.constant.DataTypeConstant;
import org.mango.jmedis.memory.local.datatype.SDS;

/**
 * @Description String数据类型
 * @Date 2021-10-23 15:14
 * @Created by mango
 */
public class JMedisString extends BaseData implements IType {

    private SDS key;
    private SDS value;

    public JMedisString(SDS key,SDS value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String typeName() {
        return DataTypeConstant.STRING;
    }

    public SDS getKey() {
        return key;
    }

    public SDS getValue() {
        return value;
    }
}
