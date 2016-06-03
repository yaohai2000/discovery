package com.bhz.netty.ch4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class MyLogicHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf msgBuf = (ByteBuf)msg;
		msgBuf.skipBytes(2);
		long size = msgBuf.readUnsignedInt();
		msgBuf.readerIndex(0);
		msgBuf.skipBytes(10);
		if(size != msgBuf.readableBytes()){
			ctx.writeAndFlush(Unpooled.copiedBuffer("Invalid package length!",CharsetUtil.ISO_8859_1));
			return;
		}
		int a = msgBuf.readInt();
		ByteBuf b = msgBuf.readBytes((int)(size-4));
		System.out.println(new String(b.array()));
		ctx.writeAndFlush(b);
	}

}
