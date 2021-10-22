package org.mango.jmedis.datatype;

import java.nio.charset.StandardCharsets;

/**
 * @Description redis sds 数据结构实现
 * @Date 2021-10-21 21:29
 * @Created by mango
 */
public class SDS {
    /**
     * 长度
     */
    private int len;
    /**
     * 空闲长度
     */
    private int free;
    /**
     * 数据
     */
    private byte[] buff;

    /**
     * 构造函数
     * @param value 字符串数据
     */
    public SDS(String value){
        this.buff = value.getBytes(StandardCharsets.UTF_8);
        this.len = this.buff.length;
        this.free = 0;
    }

    /**
     * 设置值，如果是变大，则扩容2倍
     * @param value 字符串值
     * @return SDS
     */
    public SDS setValue(String value){
        // 变大还是变小
        byte[] data = value.getBytes(StandardCharsets.UTF_8);
        int afterLen = data.length;
        int total = this.len + this.free;
        boolean needResize = false;
        if(afterLen > this.len){
            // 变大
            if(total / afterLen < 2){
                // 需要扩容
                needResize = true;
            }
        }else{
            // 变小
            if(total / afterLen > 2){
                // 超出2倍，需要缩容
                needResize = true;
            }
        }
        if(needResize){
            resize(data,afterLen);
        }else{
            // 不需要扩缩容，用原来的字节数组
            for(int i=0;i<afterLen;i++){
                this.buff[i] = data[i];
            }
            this.len = afterLen;
            this.free = total - afterLen;
        }
        return this;
    }

    /**
     * 保持大小为 len=free
     * @param data 数据
     * @param afterLen 变更后长度
     */
    private void resize(byte[] data, int afterLen) {
        byte[] newBuffer = new byte[afterLen * 2];
        for (int i = 0; i < afterLen; i++) {
            newBuffer[i] = data[i];
        }
        this.free = afterLen;
        this.buff = newBuffer;
        this.len = afterLen;
    }

    /**
     * 获取数据字节
     * @return
     */
    public byte[] getBytes(){
        byte[] data = new byte[this.len];
        for(int i=0;i<this.len;i++){
            data[i] = this.buff[i];
        }
        return data;
    }

    /**
     * 获取数据字符串
     * @return
     */
    public String getString(){
        return new String(getBytes(),StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "SDS{" +
                "len=" + len +
                ", free=" + free +
                ", buff.hashCode=" + buff.hashCode() +
                ", value=" + getString() +
                '}';
    }
}
