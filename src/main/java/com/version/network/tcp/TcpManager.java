package com.version.network.tcp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.version.common.util.LoggerUtil;
import com.version.network.netty.ClientNettyHandler;
import com.version.network.netty.SeverNettyHandler;
import com.version.network.netty.NettyDecoder;
import com.version.network.netty.NettyEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class TcpManager {
	private static TcpManager tcpServerManager = new TcpManager();

	public static TcpManager getManager() {
		return tcpServerManager;
	}

	private TcpManager() {
		int cpuNum = Runtime.getRuntime().availableProcessors();
		long totalMemary =  Runtime.getRuntime().totalMemory();
		long freeMemary = Runtime.getRuntime().freeMemory();
		long maxMemaryUse = Runtime.getRuntime().maxMemory();
		LoggerUtil.info("========================================>当前服务器CUP个数:{}", cpuNum);
		LoggerUtil.info("========================================>当前服务器总内存:{}MB", totalMemary / (1024*1024));
		LoggerUtil.info("======================================>当前服务器空闲内存:{}MB", freeMemary / (1024*1024));
		LoggerUtil.info("===================================>当前服务器总最大容量内存:{}MB", maxMemaryUse / (1024*1024));
	}

	public void createSeverTcp(String netIp, String netPort) throws Exception {
	
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 4 * 1024)// 链接缓冲池队列大小
					.option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.SO_REUSEADDR, true)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childOption(ChannelOption.SO_SNDBUF, 4 * 1024)
					.childOption(ChannelOption.SO_RCVBUF, 4 * 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							/*ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new StringEncoder());*/
							ch.pipeline().addLast(new NettyDecoder());
							ch.pipeline().addLast(new NettyEncoder());
							ch.pipeline().addLast(new IdleStateHandler(15,15,15, TimeUnit.SECONDS));
							ch.pipeline().addLast(new SeverNettyHandler());
						}
					});
			// 绑定端口
            InetAddress myip= null;
            try {
                myip = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
			b.bind(myip, Integer.parseInt(netPort)).sync();
			LoggerUtil.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>TCP BIND IP:{},LISTEN PORT:{}", myip, netPort);
			//f.channel().closeFuture().sync();
		} catch (Exception e) {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			LoggerUtil.error(ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}
	public void createClientTcp(String netIp, String netPort) throws Exception {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);

		try {
			Bootstrap b = new Bootstrap();
			b.group(bossGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 4 * 1024)// 链接缓冲池队列大小
					.option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.SO_REUSEADDR, true)					/*.childOption(ChannelOption.TCP_NODELAY, true)
					.childOption(ChannelOption.SO_SNDBUF, 4 * 1024)
					.childOption(ChannelOption.SO_RCVBUF, 4 * 1024)*/
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyDecoder());
							ch.pipeline().addLast(new NettyEncoder());
							ch.pipeline().addLast(new ClientNettyHandler());
						}
					});
			// 绑定端口
			//b.bind(netIp, Integer.parseInt(netPort)).sync();
			  b.connect(netIp, Integer.parseInt(netPort)).sync();
			// f.channel().closeFuture().sync();  
			LoggerUtil.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>TCP BIND IP:{},LISTEN PORT:{}", netIp, netPort);
		} catch (Exception e) {
			bossGroup.shutdownGracefully();

			LoggerUtil.error(ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}

    public static void main(String[] args) {
        InetAddress myip= null;
        try {
            myip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("你的IP地址是："+myip.getHostAddress());

        System.out.println("主机名为："+myip.getHostName()+"。");
    }
}
