package org.mango.jmedis.core.netty;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.mango.jmedis.core.netty.handler.MessageHandler;

import java.nio.charset.Charset;

public class JMedisInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel channel){
        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024))
        // 解码转String
        .addLast(new StringDecoder(Charset.forName("utf-8")))
        // 解码转String
        .addLast(new StringEncoder(Charset.forName("utf-8")))
        .addLast(new MessageHandler());
    }
}
