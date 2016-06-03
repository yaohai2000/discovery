package com.bhz.netty.ch4;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MyNettyServer {
	private void bind() throws Exception{
		ServerBootstrap b = new ServerBootstrap();
		EventLoopGroup g1 = new NioEventLoopGroup();
		EventLoopGroup g2 = new NioEventLoopGroup();
		try{
			b.group(g1, g2)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("lengthFieldFrameDecoder", new LengthFieldBasedFrameDecoder(1024,2,4,4,0));
					ch.pipeline().addLast(new MyLogicHandler());
				}
				
			});
			ChannelFuture cf = b.bind(new InetSocketAddress("localhost",4088)).sync();
			cf.addListener(new ChannelFutureListener(){

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					System.out.println("The server is started and listening on port 4088");
				}
				
			});
			cf.channel().closeFuture().sync();
		}finally{
			g1.shutdownGracefully();
			g2.shutdownGracefully();
		}
	}
	
	public static void main(String[] args){
		try{
			MyNettyServer s = new MyNettyServer();
			s.bind();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
