package com.bhz.netty.ch1;

import java.text.SimpleDateFormat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class TimeServer {
	private void bind(int port) throws Exception{
		EventLoopGroup acceptor = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		try{
			b.group(acceptor,worker)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChannelInitializer<SocketChannel>(){
	
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
						sc.pipeline().addLast(new StringDecoder());
						sc.pipeline().addLast(new TimeServerHandler());
					}
					
				});
			ChannelFuture f = b.bind(port).sync();
			f.addListener(new ChannelFutureListener(){
	
				@Override
				public void operationComplete(ChannelFuture cf) throws Exception {
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
			System.out.println("Usage: " + TimeServer.class.getSimpleName() + " [port]");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
		TimeServer ts = new TimeServer();
		ts.bind(port);
	}
}

@ChannelHandler.Sharable
class TimeServerHandler extends ChannelHandlerAdapter{
	private int counter = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf b = (ByteBuf)msg;
//		String cmd = b.toString(CharsetUtil.UTF_8);
		String cmd = (String)msg;
//		cmd = cmd.substring(0, cmd.indexOf(System.getProperty("line.separator")));
		String result = sdf.format(System.currentTimeMillis());
		if(cmd.toLowerCase().equals("query time")){
			counter++;
			ctx.writeAndFlush(Unpooled.copiedBuffer(result + " [The counter is :" + counter + "]" + System.getProperty("line.separator"),CharsetUtil.UTF_8));
		}else{
			ctx.writeAndFlush(Unpooled.copiedBuffer("Bad Command" + System.getProperty("line.separator"),CharsetUtil.UTF_8));
		}
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
