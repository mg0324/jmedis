package org.mango.jmedis.ehandler;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.command.CmdExecutor;
import org.mango.jmedis.config.ServerConf;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.response.ResponseFormatter;
import org.mango.jmedis.server.IServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Description 命令请求处理器
 * @Date 2021-10-21 23:24
 * @Created by mango
 */
public class CommandRequestHandler implements EventHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    // 服务器连接
    private IServer server;
    // 响应格式化器
    private ResponseFormatter responseFormatter;
    // 命令执行器
    private CmdExecutor cmdExecutor;

    public CommandRequestHandler(){
        // 初始化响应格式化器
        responseFormatter = new ResponseFormatter();
        cmdExecutor = new CmdExecutor();
    }

    /**
     * 处理命令
     */
    @Override
    public void handle(IServer server,JMedisClient client) throws IOException {
        this.server = server;
        //设置一个读取数据的Buffer
        ByteBuffer buff = ByteBuffer.allocate(ServerConf.BUFFER_SIZE);
        int size = client.getConn().read(buff);
        if(size == -1){
            // 异常退出或者链接关闭
            log.debug("client[{}] exit",client.getClientKey());
            client.close();
            return ;
        }
        //在key上携带一个附件(附近就是之后要写的内容)
        //key.attach("服务端已收到:"+content);
        String command = new String(buff.array(),0,size, StandardCharsets.UTF_8);
        // 去掉回车换行 \r\n
        command = command.replaceAll("\\r\\n","");
        log.info("receive command is {}",command);
        // 执行命令
        CmdResponse cmdResponse = cmdExecutor.execute(client,command);
        if(Objects.nonNull(cmdResponse)){
            // 将结果返回给客户端
            this.server.render(client,responseFormatter.format(cmdResponse));
        }
    }
}
