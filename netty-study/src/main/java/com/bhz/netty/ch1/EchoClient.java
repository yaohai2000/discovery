package com.bhz.netty.ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class EchoClient {
	private void connect(String host, int port, String msg) throws Exception{
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		try{
			b.group(worker)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new EchoClientHandler(msg));
				}
				
			});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		}finally{
			worker.shutdownGracefully();
		}
	}
	public static void main(String[] args) throws Exception{
		EchoClient ec = new EchoClient();
		if(args.length !=3){
			System.out.println("Usage: " + EchoClient.class.getSimpleName() + " [host] [port] [message]");
			System.exit(0);
		}
		ec.connect(args[0], Integer.parseInt(args[1]), args[2]);
	}
}

class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
	
	final String msg;
	
	public EchoClientHandler(String msg) {
		super();
		this.msg = msg;
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
