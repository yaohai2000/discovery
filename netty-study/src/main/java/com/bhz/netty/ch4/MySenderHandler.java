package com.bhz.netty.ch4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class MySenderHandler extends SimpleChannelInboundHandler<AttributeKey<Integer>> {
	AttributeKey<Integer> key ;
	public MySenderHandler(AttributeKey<Integer> key) {
		this.key = key;
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf b1 = genPackage("Hello World!");
		ByteBuf b2 = genPackage("Netty is very powerful!");
		ByteBuf b = Unpooled.copiedBuffer(b2,b1);
		ctx.writeAndFlush(b);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, AttributeKey<Integer> msg) throws Exception {
		System.out.println("[ " + ctx.channel().attr(key).get() + " ]" + msg);
	}
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().close();
	}

	private ByteBuf genPackage(String content){
		ByteBuf bb = Unpooled.buffer();
		bb.writeByte(0x0A).writeByte(0x0B);
		int contentSize = content.getBytes().length;
		int packageLength = 4 + contentSize;
		bb.writeInt(packageLength);
		bb.writeByte(0x02);
		bb.writeShort(35);
		bb.writeByte(0x1F);
		bb.writeInt(9999);
		bb.writeBytes(content.getBytes());
		return bb;
	}
	
}
