package org.mango.jmedis.ehandler;

import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.server.IServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @Description 接收事件处理器
 * @Date 2021-10-21 23:22
 * @Created by mango
 */
public class AcceptEventHandler implements EventHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(IServer server) throws IOException {
        //从serverSocketChannel中获取socketChannel信息
        SocketChannel socketChannel = server.getServer().accept();
        //设置为非阻塞模式
        socketChannel.configureBlocking(false);
        //将连接注册到多路复用器上监听读事件
        socketChannel.register(server.getSelector(), SelectionKey.OP_READ);
        log.debug("accept client is {}",socketChannel.getRemoteAddress());
        // 保存客户端到服务器对象的clientMap
        JMedisClient client = new JMedisClient(socketChannel);
        server.addClient(client);
    }
}
