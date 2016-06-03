package com.bhz.netty.ch4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;

public class MyNettyClient implements Runnable{
	int idx ;
	public MyNettyClient(){
		
	}
	public MyNettyClient(int idx){
		this.idx = idx;
	}
	private void connect() throws Exception{
		Bootstrap b = new Bootstrap();
		EventLoopGroup g = new NioEventLoopGroup();
		b.group(g).channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		.handler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				AttributeKey<Integer> ak = AttributeKey.valueOf("Thread-" + idx);
				ch.attr(ak).set(idx);
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new MySenderHandler(ak));
			}
			
		});
		ChannelFuture cf = b.connect("localhost", 4088).sync();
		cf.channel().closeFuture().sync();
	}
	
	public static void main(String[] args) throws Exception{
		int threadCount = 5;
		if(args.length == 1){
			threadCount = Integer.parseInt(args[0]);
		}
		ExecutorService es = Executors.newFixedThreadPool(10);
		for(int i=0;i<threadCount;i++){
			MyNettyClient mc = new MyNettyClient(i);
			es.submit(mc);
		}
	}

	@Override
	public void run() {
		try {
			connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
