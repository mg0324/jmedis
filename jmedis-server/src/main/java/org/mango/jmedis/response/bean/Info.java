package org.mango.jmedis.response.bean;

import java.util.List;

/**
 * @Description 信息
 * @Date 2021-10-30 09:52
 * @Created by mango
 */
public class Info implements java.io.Serializable{

    private List<InfoBlock> blockList;

    public List<InfoBlock> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<InfoBlock> blockList) {
        this.blockList = blockList;
    }
}
