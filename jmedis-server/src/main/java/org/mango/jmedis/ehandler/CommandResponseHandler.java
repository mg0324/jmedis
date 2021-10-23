package org.mango.jmedis.ehandler;

import org.mango.jmedis.client.JMedisClient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @Description 命令应答处理器
 * @Date 2021-10-21 23:24
 * @Created by mango
 */
public class CommandResponseHandler implements EventHandler{

    @Override
    public void handle(JMedisClient client, String msg) throws IOException {
        client.getConn().write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
    }
}
