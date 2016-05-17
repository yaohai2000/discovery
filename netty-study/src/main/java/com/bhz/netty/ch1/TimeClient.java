package com.bhz.netty.ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class TimeClient {
	private void connect(String host,int port, String cmd) throws Exception{
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		try{
			b.group(worker)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>(){
	
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(new TimeClientHandler(cmd));
					}
					
				});
			ChannelFuture cf = b.connect(host, port).sync();
			cf.channel().closeFuture().sync();
		}finally{
			worker.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception{
		if(args.length !=3){
			System.out.println("Usage: " + TimeClient.class.getSimpleName() + " [host] [port] [message]");
			System.exit(0);
		}
		TimeClient tc = new TimeClient();
		tc.connect(args[0], Integer.parseInt(args[1]), args[2]);
	}
}

class TimeClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
	final String msg;
	
	public TimeClientHandler(String cmd){
		this.msg = cmd;
	}
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println(msg.toString(CharsetUtil.UTF_8));
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Sending message to server: " + msg);
		ctx.writeAndFlush(Unpooled.copiedBuffer(msg,CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}
