package org.mango.jmedis.core.ehandler;

import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.constant.JMedisConstant;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Description 命令请求处理器
 * @Date 2021-10-21 23:24
 * @Created by mango
 */
@Slf4j
public class CommandRequestHandler implements EventHandler{

    /**
     * 处理命令
     * @param socketChannel
     */
    @Override
    public void handle(SocketChannel socketChannel) throws IOException {
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
        log.info("receive command is {}",command);

        commandExecute(command);

    }

    /**
     * 执行命令
     * @param command
     */
    private static void commandExecute(String command) {
        log.debug("execute command:{}",command);
    }
}
