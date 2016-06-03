package com.bhz.netty.ch3;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTest1 {
	public static void main(String[] args) throws UnsupportedEncodingException{
		ByteBuf header = Unpooled.buffer();
		ByteBuf body = Unpooled.buffer();
		header.writeBytes("Header".getBytes());
		body.writeBytes("Body".getBytes());
		
		CompositeByteBuf cbuf = Unpooled.compositeBuffer();
		cbuf.addComponent(header);
		cbuf.addComponent(body);
		for(Iterator<ByteBuf> it=cbuf.iterator();it.hasNext();){
			ByteBuf b = it.next();
			System.out.println(new String(b.array()));
		}
//		System.out.println(new String(d));
	}
}
