package org.mango.jmedis.ehandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Description 命令应答处理器
 * @Date 2021-10-21 23:24
 * @Created by mango
 */
public class CommandResponseHandler implements EventHandler{

    @Override
    public void handle(SocketChannel socketChannel, String msg) throws IOException {
        socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
    }
}
