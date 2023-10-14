package org.mango.jmedis.response.bean;

import java.util.Map;

/**
 * @Description 信息块
 * @Date 2021-10-30 09:52
 * @Created by mango
 */
public class InfoBlock implements java.io.Serializable{
    private String name;
    private Map<String,String> props;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }
}
