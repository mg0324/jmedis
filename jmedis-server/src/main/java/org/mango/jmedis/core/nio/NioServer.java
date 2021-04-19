package org.mango.jmedis.core.nio;

import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.core.IServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NioServer implements IServer {
    int port;
    Selector selector;
    ServerSocketChannel server;
    @Override
    public void start(int port) {
        try {
            this.port = port;
            this.selector = Selector.open();
            //打开监听通道
            this.server = ServerSocketChannel.open();
            //绑定端口
            server.bind(new InetSocketAddress(this.port));
            //默认configureBlocking为true,如果为 true,此通道将被置于阻塞模式；如果为 false.则此通道将被置于非阻塞模式
            server.configureBlocking(false);
            //创建选择器
            selector = Selector.open();
            //监听客户端连接请求
            server.register(selector, SelectionKey.OP_ACCEPT);
            log.debug("服务端启动成功，监听端口：" + port);
            while(true){
                //阻塞方法，轮询注册的channel,当至少一个channel就绪的时候才会继续往下执行
                int keyCount = selector.select(3000);
                log.debug("当前有:"+keyCount+"channel有事件就绪");
                //获取就绪的SelectionKey
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                //迭代就绪的key
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    //SelectionKey相当于是一个Channel的表示，标记当前channel处于什么状态
                    // 按照channel的不同状态处理数据
                    process(key);
                }
            }

        }catch (Exception e){
            log.error("服务器启动失败",e);
        }
    }

    private void process(SelectionKey key) throws IOException {
        //该channel已就绪，可接收消息
        if(key.isAcceptable()){
            log.info("accept事件就绪...");
            doAccept(key);
        }else if(key.isReadable()){
            log.info("read事件就绪...");
            doRead(key);
        }else if(key.isWritable()){
            log.info("write事件就绪...");
            doWrite(key);
        }
    }
    private void doWrite(SelectionKey key) throws IOException {
        //获取对应的socket
        SocketChannel socket = (SocketChannel)key.channel();
        //获取key上的附件
        String content = (String)key.attachment();
        socket.write(ByteBuffer.wrap(content.getBytes()));
        //socket.close();
    }

    private void doRead(SelectionKey key) throws IOException {
        //获取对应的socket
        SocketChannel socket = (SocketChannel)key.channel();
        //设置一个读取数据的Buffer 大小为1024
        ByteBuffer buff = ByteBuffer.allocate(1024);
        StringBuilder content = new StringBuilder();

        while(socket.read(buff) > 0) {
            buff.flip();
            content.append(new String(buff.array(),"utf-8"));
        }
        //注册selector，并设置为可写模式
        //key = socket.register(selector,SelectionKey.OP_WRITE);
        //在key上携带一个附件(附近就是之后要写的内容)
        //key.attach("服务端已收到:"+content);
        log.info("读取内容：" + content);
        socket.write(ByteBuffer.wrap(content.toString().getBytes()));
    }

    private void doAccept(SelectionKey key) throws IOException {
        //获取对应的channel
        ServerSocketChannel server = (ServerSocketChannel)key.channel();
        //从channel中获取socket信息
        SocketChannel socket = server.accept();
        //设置为非阻塞模式
        socket.configureBlocking(false);
        //注册selector，并设置为可读模式erw
        socket.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void stop() throws IOException {
        if(selector.isOpen()){
            selector.close();
        }
        if(server.isOpen()){
            server.close();
        }
    }
}
