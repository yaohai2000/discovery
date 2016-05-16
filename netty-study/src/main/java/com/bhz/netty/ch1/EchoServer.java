package com.bhz.netty.ch1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class EchoServer{
	private void bind(int port) throws Exception{
		EventLoopGroup acceptor = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		try{
			b.group(acceptor, worker)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChannelInitializer<SocketChannel>(){
	
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new EchoServerHandler());
				}
				
			});
			ChannelFuture f = b.bind(port).sync();
			f.addListener(new ChannelFutureListener(){

				@Override
				public void operationComplete(ChannelFuture arg0) throws Exception {
					System.out.println("The Server is listening on port " + port + " and waiting for connection");
				}
				
			});
			f.channel().closeFuture().sync();
		}finally{
			worker.shutdownGracefully();
			acceptor.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception{
		if(args.length != 1){
			System.out.println("Usage: " + EchoServer.class.getSimpleName() + " [port]");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
		EchoServer ec = new EchoServer();
		ec.bind(port);
	}
}

class EchoServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Channel Read");
		ByteBuf in = (ByteBuf)msg;
		System.out.println("Server received message: " + in.toString(CharsetUtil.UTF_8));
		
		ByteBuf out = Unpooled.copiedBuffer("From server. Just you say: " ,CharsetUtil.UTF_8);
		out.writeBytes(in);
		ctx.write(out);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
}
