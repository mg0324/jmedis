package org.mango.jmedis.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NettyServer {

	// 保存客户端连接的通道引用
	public static Map<String,SocketChannel> _scMap_ = null;


	public static void start(int port){
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup(4);
		_scMap_ = new ConcurrentHashMap<>();
		ServerBootstrap bootstrap = new ServerBootstrap();
		// 添加boss和worker组
		bootstrap.group(boss, worker);
		//这句是指定允许等待accept的最大连接数量,我只需要连一个客户端,这里就关掉了,java默认是50个
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		// 用于构造socketchannel工厂
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new JMedisInitializer());

		// 绑定端口，开始接收进来的连接
		ChannelFuture f;
		try {
			f = bootstrap.bind(port).sync();
			log.info("NettyServer监听"+port);
			// 等待服务器 socket 关闭 。
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

}

