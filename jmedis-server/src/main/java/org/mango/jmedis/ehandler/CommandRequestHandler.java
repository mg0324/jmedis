package org.mango.jmedis.ehandler;

import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.command.ICmd;
import org.mango.jmedis.command.tip.PingCmd;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.server.IServer;
import org.mango.jmedis.util.StringUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 命令请求处理器
 * @Date 2021-10-21 23:24
 * @Created by mango
 */
@Slf4j
public class CommandRequestHandler implements EventHandler{

    private SocketChannel socketChannel;
    private IServer server;

    private Map<String, ICmd> cmdMap;

    public CommandRequestHandler(){
        cmdMap = new HashMap<>();
        // 注册命令执行器
        cmdMap.put(JMedisConstant.CMD_PING, new PingCmd());
    }

    /**
     * 处理命令
     * @param socketChannel
     */
    @Override
    public void handle(IServer server,SocketChannel socketChannel) throws IOException {
        this.server = server;
        this.socketChannel = socketChannel;
        //设置一个读取数据的Buffer
        ByteBuffer buff = ByteBuffer.allocate(JMedisConstant.BUFFER_SIZE);
        int size = socketChannel.read(buff);
        if(size == -1){
            // 异常退出
            log.debug("{} exception exit",socketChannel.getRemoteAddress());
            socketChannel.close();
            return ;
        }
        //在key上携带一个附件(附近就是之后要写的内容)
        //key.attach("服务端已收到:"+content);
        String command = new String(buff.array(),0,size, StandardCharsets.UTF_8);
        // 去掉回车换行 \r\n
        command = command.replaceAll("\\r\\n","");
        log.info("receive command is {}",command);
        commandExecute(command);
    }

    /**
     * 执行命令
     * @param command
     */
    private void commandExecute(String command) throws IOException {
        log.debug("execute command:{}",command);
        if(StringUtil.isNotBlank(command)) {
            String[] arr = command.split(" ");
            String cmdType = arr[0];
            ICmd cmd = cmdMap.get(cmdType.toUpperCase());
            if (null == cmd) {
                log.warn("command[{}] not support!", cmdType);
            } else {
                // 执行并得到结果
                String result = cmd.execute(oneStartArr(arr));
                // 将结果返回给客户端
                this.server.render(socketChannel,result);
            }
        }
    }

    /**
     * 取数据从1开始的数据到新数组
     * @param arr
     * @return
     */
    private String[] oneStartArr(String[] arr){
        String[] result = new String[]{};
        for(int i=1;i<arr.length;i++){
            result[i] = arr[i];
        }
        return result;
    }
}
