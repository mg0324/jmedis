package org.mango.jmedis.core;

import org.mango.jmedis.core.config.ServerConf;
import org.mango.jmedis.util.StringUtil;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @Description 客户端
 * @Date 2021-10-23 14:11
 * @Created by mango
 */
public class JMedisClient {
    // 连接
    private SocketChannel conn;
    // 数据库下标
    private int dbIndex;
    // 通过认证
    private boolean passAuth = true;

    public JMedisClient(SocketChannel conn){
        this.conn = conn;
        // 默认选择下标为0的数据库
        this.dbIndex = 0;
        // 设置认证标准，如果没有密码则直接认证通过
        if(StringUtil.isNotBlank(ServerConf.getConf().getAuthPassword())){
            // 需要
            this.passAuth = false;
        }
    }

    /**
     * 获取数据库下标
     * @return
     */
    public int getDbIndex(){
        return dbIndex;
    }

    /**
     * 获取客户端key
     * @return
     * @throws IOException
     */
    public String getClientKey() throws IOException {
        return conn.getRemoteAddress().toString();
    }

    /**
     * 关闭连接
     * @throws IOException
     */
    public void close() throws IOException {
        ClientFactory.current().remove(getClientKey());
        if(null != conn){
            conn.close();
        }
    }

    /**
     * 切换数据库
     * @param index 数据库下标
     */
    public void select(int index){
        this.dbIndex = index;
    }

    public boolean isPassAuth() {
        return passAuth;
    }

    public void setPassAuth(boolean passAuth) {
        this.passAuth = passAuth;
    }

    /**
     * 获取客户端连接
     * @return
     */
    public SocketChannel getConn(){
        return this.conn;
    }
}
