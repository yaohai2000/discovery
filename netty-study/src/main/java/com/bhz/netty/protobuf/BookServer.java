package com.bhz.netty.protobuf;

import com.bhz.netty.protobuf.BookProto.BookMsg;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class BookServer {
	private void bind() throws Exception{
		ServerBootstrap b = new ServerBootstrap();
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		b.group(boss, worker)
			.option(ChannelOption.SO_BACKLOG, 128)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("ProtoBufUnwrapper",new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast("ProtoBufWrapper",new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast("ProtoBufEncoder",new ProtobufEncoder());
					ch.pipeline().addLast("ProtoBufDecoder", new ProtobufDecoder(BookMsg.getDefaultInstance()));
					ch.pipeline().addLast("BookMsgHandler",new BookMsgHandler());
				}
				
			});
		ChannelFuture cf = b.bind(1090).sync();
		cf.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("Server is started.");
				
			}
		});
		cf.channel().closeFuture().sync();
	}
	
	public static void main(String[] args) throws Exception{
		BookServer bs = new BookServer();
		bs.bind();
	}
}


class BookMsgHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		BookMsg book = (BookMsg)msg;
		if(book.getBooks().getBookId().equals("10000")){
			ctx.writeAndFlush(createBookMsg());
		}else{
			throw new Exception("Invalid Message!");
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	private BookProto.BookMsg createBookMsg(){
		BookProto.BookMsg.Builder bsb = BookProto.BookMsg.newBuilder();
		BookProto.Author.Builder authorBuilder = BookProto.Author.newBuilder();
		BookProto.Book.Builder bookBuilder = BookProto.Book.newBuilder();
		authorBuilder.setAuthorName("Terry");
		authorBuilder.setAddress("Beijing Chaoyang");
		bookBuilder.setBookId("10000");
		bookBuilder.setBookName("Netty book");
		bookBuilder.setPrice(87.64f);
		BookProto.Book b = bookBuilder.build();
		authorBuilder.addBooks(b);
		bsb.setAuthor(authorBuilder.build());
		bsb.setBooks(b);
		return bsb.build();
	}
	
}