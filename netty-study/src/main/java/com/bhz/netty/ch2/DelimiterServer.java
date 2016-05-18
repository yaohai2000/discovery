package com.bhz.netty.ch2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class DelimiterServer {
	public static void main(String[] args) throws Exception{
		DelimiterServer ds = new DelimiterServer();
		ds.bind(9002);
	}
	private void bind(int port) throws Exception{
		EventLoopGroup acceptor = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		ByteBuf delimiter = Unpooled.copiedBuffer("&".getBytes());
		try{
			b.group(acceptor,worker)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>(){
	
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipe = ch.pipeline();
						pipe.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
						pipe.addLast(new StringDecoder());
						pipe.addLast(new DelimiterServerHandler());
					}
					
				});
			ChannelFuture f = b.bind(port).sync();
			f.addListener(new ChannelFutureListener(){

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					System.out.println("The server is started and listening on port: " + port);
				}
				
			});
			f.channel().closeFuture().sync();
		}finally{
			worker.shutdownGracefully();
			acceptor.shutdownGracefully();
		}
	}
}

class DelimiterServerHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String m = (String)msg;
		System.out.println(m);
	}
	
}
