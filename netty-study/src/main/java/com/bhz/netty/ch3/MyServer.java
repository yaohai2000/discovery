package com.bhz.netty.ch3;

import java.util.List;

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
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.ReplayingDecoder;

public class MyServer {
	
	public static void main(String[] args) throws Exception{
		MyServer ms = new MyServer();
		ms.startServer();
	}
	
	private void startServer() throws Exception{
		ServerBootstrap sb = new ServerBootstrap();
		EventLoopGroup acceptor = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		sb.group(acceptor,worker)
		.channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 1024)
		.childOption(ChannelOption.TCP_NODELAY, true)
		.childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
				sc.pipeline().addLast(new MyMessageDecoder());
				sc.pipeline().addLast(new MyHandler());
			}
			
		});
		ChannelFuture f = sb.bind(4099).sync();
		
		f.addListener(new ChannelFutureListener(){

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("Server started and listening on port : 4099" );
			}
			
		});
		f.channel().closeFuture().sync();
	}
	
	class MyMessageDecoder extends ReplayingDecoder{

		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
			System.out.println("Readable bytes:" + in.readableBytes());
			
			System.out.println("Reader index: "+in.readerIndex());
			System.out.println("Writer index: "+in.writerIndex());
			byte[] bookName = new byte[20];
			in.readBytes(bookName);
			out.add(new String(bookName).trim());
			byte[] category = new byte[5];
			in.readBytes(category);
			out.add(new String(category).trim());
//			double d = in.readDouble();
//			out.add(d);
		}
		
	}
	
	class MyHandler extends ChannelHandlerAdapter{

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println(msg);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// TODO Auto-generated method stub
			super.exceptionCaught(ctx, cause);
		}
		
	}
}
