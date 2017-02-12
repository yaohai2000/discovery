package com.bhz.netty.protobuf;

import com.bhz.netty.protobuf.BookProto.BookMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class BookClient {
	private void connect() throws Exception{
		Bootstrap b = new Bootstrap();
		EventLoopGroup e = new NioEventLoopGroup();
		b.group(e)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(new ProtobufDecoder(BookProto.BookMsg.getDefaultInstance()));
					ch.pipeline().addLast(new BookMsgClientHandler());
				}
				
			});
		ChannelFuture cf = b.connect("localhost", 1090).sync();
		cf.channel().closeFuture().sync();
	}
	
	public static void main(String[] args) throws Exception{
		BookClient bc = new BookClient();
		bc.connect();
	}
}

class BookMsgClientHandler extends SimpleChannelInboundHandler<BookProto.BookMsg>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BookMsg msg) throws Exception {
		if(msg instanceof BookProto.BookMsg){
			BookProto.BookMsg bs = (BookProto.BookMsg)msg;
			System.out.println(bs.getBooks().getBookName() + "  " + bs.getAuthor().getAuthorName()); 
			System.out.println(bs.getBooks().getPrice() + "  " +bs.getBooks().getBookName() + "  " + bs.getAuthor().getAuthorName()); 
		}else{
			System.out.println(msg.toString());
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		BookMsg.Builder b = BookMsg.newBuilder();
		BookProto.Book.Builder bookB = BookProto.Book.newBuilder();
		bookB.setBookId("10000");
		b.setBooks(bookB.build());
		ctx.writeAndFlush(b.build());
	}
	
	
	
}
