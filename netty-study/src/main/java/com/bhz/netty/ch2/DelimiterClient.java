package com.bhz.netty.ch2;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class DelimiterClient {
	
	public static void main(String[] args) throws Exception{
		DelimiterClient dc = new DelimiterClient();
		dc.connect("localhost", 9002);
	}
	
	private void connect(String host, int port) throws Exception{
		EventLoopGroup g = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		try{
			b.group(g)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>(){

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new DelimiterClientHandler());
					}
					
				});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		}finally{
			g.shutdownGracefully();
		}
	}
	
	class DelimiterClientHandler extends ChannelInboundHandlerAdapter{

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			ByteBuf b = Unpooled.copiedBuffer("You & Me & cc & very fast!&".getBytes());
			ctx.writeAndFlush(b);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ReferenceCountUtil.release(msg);
		}
		
	}
}
