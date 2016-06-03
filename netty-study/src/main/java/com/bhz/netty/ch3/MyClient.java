package com.bhz.netty.ch3;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
	private void connect() throws InterruptedException{
		Bootstrap b = new Bootstrap();
		EventLoopGroup elg = new NioEventLoopGroup();
		b.group(elg)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ChannelHandlerAdapter(){

						@Override
						public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
							// TODO Auto-generated method stub
							super.exceptionCaught(ctx, cause);
						}
						
						@Override
						public void channelActive(ChannelHandlerContext ctx){
							ByteBuf b = Unpooled.buffer();
							b.writeInt(25);
							byte[] b1 = new byte[20];
							System.arraycopy("Netty In Action     ".getBytes(), 0, b1, 0, 20);
							b.writeBytes(b1);
							byte[] b2 = new byte[5];
							System.arraycopy("Java ".getBytes(), 0, b2, 0, 5);
							b.writeBytes(b2);
//							b.writeDouble(34.56);
							ctx.writeAndFlush(b);
						}

//						@Override
//						public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
//								throws Exception {
//							
////							b.writeBytes("Netty In Action".getBytes());
//							
//						}
						
					});
//					ch.pipeline().addLast(new ChannelHandlerAdapter(){
//
//						@Override
//						public void channelActive(ChannelHandlerContext ctx) throws Exception {
//							ctx.write(Unpooled.EMPTY_BUFFER);
////							ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
//						}
//						
//					});
				}
				
			});
		ChannelFuture cf = b.connect(new InetSocketAddress("localhost",4099)).sync();
		cf.addListener(new ChannelFutureListener(){

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("Server Connected!");
			}
			
		});
		cf.channel().closeFuture().sync();
		
	}
	
	public static void main(String[] args) throws InterruptedException{
		MyClient mc = new MyClient();
		mc.connect();
	}
	
}
