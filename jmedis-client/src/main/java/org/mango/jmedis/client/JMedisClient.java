package org.mango.jmedis.client;

import org.mango.jmedis.cmdHandler.CmdHandler;
import org.mango.jmedis.cmdHandler.impl.SelectCmdHandler;
import org.mango.jmedis.config.ClientConf;
import org.mango.jmedis.util.ClientUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Description jmedis 客户端
 * @Date 2021-10-24 16:08
 * @Created by mango
 */
public class JMedisClient {
    // 客户端连接
    private SocketChannel conn;
    // 主机
    private String host;
    // 端口
    private int port;
    // 数据库端口
    private int dbIndex;
    // 是否存活
    private boolean isAlive = true;
    // 命令处理器责任链
    private CmdHandler cmdHandler;

    public JMedisClient(String host,int port){
        this.init(host,port,0);
    }

    public JMedisClient(String host,int port,int dbIndex){
        this.init(host,port,dbIndex);
    }
    // 初始化
    public void init(String host,int port,int dbIndex){
        this.host = host;
        this.port = port;
        this.dbIndex = dbIndex;
        // 初始化命令责任链
        this.initCmdHandler();
        // 连接服务器
        this.connServer();
    }

    private void initCmdHandler() {
        this.cmdHandler = new SelectCmdHandler();
    }

    // 连接服务器
    private void connServer() {
        try {
            conn = SocketChannel.open(new InetSocketAddress(host,port));
            // 设置阻塞
            conn.configureBlocking(true);
            // 处理连接
            doMain();
        } catch (IOException e) {
            // 连接拒绝
            ClientUtil.println("(error) jmedis server["+host+":"+port+"] connect refuse");
        }
    }
    // 主逻辑
    private void doMain() throws IOException {
        // 获取键盘输入
        Scanner scanner = new Scanner(System.in);
        while(isAlive){
            ClientUtil.showCmdLine(this);
            String cmd = scanner.nextLine();
            if("exit".equals(cmd)){
                isAlive = false;
                this.close();
                return ;
            }
            ByteBuffer data = ByteBuffer.wrap(cmd.getBytes(StandardCharsets.UTF_8));
            // 发送数据
            conn.write(data);
            // 阻塞读取数据
            if(readData()){
                // 执行成功后，执行客户端对应命令操作
                this.cmdHandler.handle(this,cmd);
            }
        }
    }
    // 阻塞读取数据
    private boolean readData() throws IOException {
        //设置一个读取数据的Buffer
        ByteBuffer buff = ByteBuffer.allocate(ClientConf.BUFFER_SIZE);
        int size = conn.read(buff);
        if(size == -1){
            // 异常退出
            this.close();
            return false;
        }
        String data = new String(buff.array(),0,size, StandardCharsets.UTF_8);
        ClientUtil.print(data);
        // 客户端根据服务端返回成功，如果是以(error)开头则失败
        if(data.startsWith("(error)")){
            return false;
        }
        return true;
    }

    /**
     * 获取连接
     * @return
     */
    public SocketChannel getConn(){
        return conn;
    }

    /**
     * 关闭连接
     */
    public void close(){
        try {
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

}
