package org.mango.jmedis.client;

import org.mango.jmedis.handler.client.ClientCmdHandler;
import org.mango.jmedis.handler.client.impl.SelectClientCmdHandler;
import org.mango.jmedis.config.ClientConf;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.handler.pre.PreHandler;
import org.mango.jmedis.handler.pre.impl.AuthPreHandler;
import org.mango.jmedis.util.ClientUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
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
    // 认证密码
    private String authPwd;
    // 是否存活
    private boolean isAlive = true;
    // 命令处理器责任链
    private ClientCmdHandler clientCmdHandler;
    // 前置处理器
    private List<PreHandler> preHandlerList;

    public JMedisClient(){}

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
        // 初始化前置处理器
        this.initPreHandler();
        // 连接服务器
        this.connServer();
    }

    private void initPreHandler() {
        this.preHandlerList = new LinkedList<>();
        // 加入认证前置处理器
        preHandlerList.add(new AuthPreHandler());
    }
    //执行前置处理器
    private void preExecute() throws IOException {
        for(PreHandler preHandler : this.preHandlerList){
            preHandler.handle();
        }
    }

    private void initCmdHandler() {
        this.clientCmdHandler = new SelectClientCmdHandler();
    }

    // 连接服务器
    private void connServer() {
        try {
            conn = SocketChannel.open(new InetSocketAddress(host,port));
            // 设置阻塞
            conn.configureBlocking(true);
            // 前置处理器执行
            this.preExecute();
            // 处理连接
            doMain();
        } catch (IOException e) {
            // 连接拒绝
            ClientUtil.println("(error) jmedis server["+host+":"+port+"] connect refuse,"+e.getMessage());
        }
    }
    // 主逻辑
    private void doMain() throws IOException {
        // 获取键盘输入
        Scanner scanner = new Scanner(System.in);
        while(isAlive){
            ClientUtil.showCmdLine(this);
            String cmd = scanner.nextLine();
            ByteBuffer data = ByteBuffer.wrap(cmd.getBytes(StandardCharsets.UTF_8));
            // 发送数据
            conn.write(data);
            // 阻塞读取数据
            if(readData()){
                // 执行成功后，执行客户端对应命令操作
                this.clientCmdHandler.handle(this,cmd);
            }
            if(JMedisConstant.QUIT.equals(cmd)){
                this.close();
                return ;
            }
        }
    }
    // 阻塞读取数据
    public boolean readData() throws IOException {
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
    public void executeCmd(String cmd) throws IOException {
        conn.write(ByteBuffer.wrap(cmd.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 关闭客户端
     */
    public void close(){
        try {
            this.isAlive = false;
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

    public String getAuthPwd() {
        return authPwd;
    }

    public void setAuthPwd(String authPwd) {
        this.authPwd = authPwd;
    }
}
