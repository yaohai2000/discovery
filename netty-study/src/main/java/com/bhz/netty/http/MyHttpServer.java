package com.bhz.netty.http;

import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;  
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;  
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;  
import static io.netty.handler.codec.http.HttpResponseStatus.OK;  
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1; 

public class MyHttpServer {
	public void startServer(){
		EventLoopGroup acceptor = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(acceptor,worker)
			.localAddress(6300)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new MyHttpHandlerInitializer());
		
		try {
			ChannelFuture cf = b.bind().sync();
			cf.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					System.out.println("The server is started and listening on port 6300");
				}
			});
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			acceptor.shutdownGracefully();
			worker.shutdownGracefully();
		}
			
	}
	
	class MyHttpHandlerInitializer extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast("codec",new HttpServerCodec());
			ch.pipeline().addLast("aggregator",new HttpObjectAggregator(1024*8));
			ch.pipeline().addLast("contentGen",new MyHttpHandler());
		}
		
	}
	
	class MyHttpHandler extends ChannelInboundHandlerAdapter{

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println(msg);
			ReferenceCountUtil.release(msg);
			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("<html><h1>Hello Netty</h1></html>"  
                    .getBytes()));  
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");  
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());  
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE); 
			
			ctx.writeAndFlush(response);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// TODO Auto-generated method stub
			super.exceptionCaught(ctx, cause);
		}
		
	}
	
	public static void main(String[] args) {
		MyHttpServer server = new MyHttpServer();
		server.startServer();
	}
	
}
