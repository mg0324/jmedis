package org.mango.jmedis.core.server.nio;

import lombok.extern.slf4j.Slf4j;
import org.mango.jmedis.constant.JMedisConstant;
import org.mango.jmedis.core.ehandler.AcceptEventHandler;
import org.mango.jmedis.core.ehandler.CommandRequestHandler;
import org.mango.jmedis.core.ehandler.CommandResponseHandler;
import org.mango.jmedis.core.ehandler.EventHandler;
import org.mango.jmedis.core.server.IServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class NioServer implements IServer {
    // 监听端口
    private int port;
    // 多路复用器
    private Selector selector;
    // 服务器对象
    private ServerSocketChannel server;
    // 事件处理器注册map
    private Map<String, EventHandler> eventHandlerMap;

    private void init(int port) throws IOException {
        this.port = port;
        //创建选择器
        this.selector = Selector.open();
        //打开监听通道
        this.server = ServerSocketChannel.open();
        // 注册事件处理器
        this.eventHandlerMap = new HashMap<>();
        eventHandlerMap.put(JMedisConstant.EVENT_ACCEPT,new AcceptEventHandler());
        eventHandlerMap.put(JMedisConstant.EVENT_COMMAND_REQUEST,new CommandRequestHandler());
        eventHandlerMap.put(JMedisConstant.EVENT_COMMAND_RESPONSE,new CommandResponseHandler());
    }
    @Override
    public void start(int port) {
        try {
            // 初始化
            this.init(port);
            //绑定端口
            this.server.bind(new InetSocketAddress(this.port));
            log.debug("jmedis server is running on {}",this.port);
            //默认configureBlocking为true,如果为 true,此通道将被置于阻塞模式；如果为 false.则此通道将被置于非阻塞模式
            this.server.configureBlocking(false);
            //监听客户端连接请求
            this.server.register(selector, SelectionKey.OP_ACCEPT);
            while(true){
                //阻塞方法，轮询注册的channel,当至少一个channel就绪的时候才会继续往下执行
                selector.select();// 阻塞
                //获取就绪的SelectionKey
                Set<SelectionKey> keys = this.selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                //迭代就绪的key
                while(it.hasNext()){
                    SelectionKey key = it.next();
                    //SelectionKey相当于是一个Channel的表示，标记当前channel处于什么状态
                    // 按照channel的不同状态处理数据
                    process(key);
                    it.remove();
                }
            }

        }catch (Exception e){
            log.error("jmedis server run error,",e);
        }
    }

    @Override
    public ServerSocketChannel getServer() {
        return server;
    }

    @Override
    public Selector getSelector() {
        return selector;
    }

    @Override
    public int getPort() {
        return port;
    }

    /**
     * 将事件分发给事件处理器处理
     * @param key
     * @throws IOException
     */
    private void process(SelectionKey key) throws IOException {
        //该channel已就绪，可接收消息
        if(key.isAcceptable()){
            eventHandlerMap.get(JMedisConstant.EVENT_ACCEPT).handle(this);
        }else if(key.isReadable()){
            eventHandlerMap.get(JMedisConstant.EVENT_COMMAND_REQUEST).handle((SocketChannel) key.channel());
        }
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
