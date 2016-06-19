package com.bhz.netty.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bhz.netty.protobuf.SubscribeReqProto;
import com.google.protobuf.InvalidProtocolBufferException;

public class SubscribeReqProtoTest {
	private static byte[] encode(SubscribeReqProto.SubscribeReq req){
		return req.toByteArray();
	}
	private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException{
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}
	
	private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("Terry Yao");
		builder.setProductName("EPS System");
		List<String> address = new ArrayList<String>();
		address.add("Nanjing YuHuaTai");
		address.add("Beijing LiuLiChang");
		address.add("ShenZhen HongShuLin");
		builder.addAllAddress(address);
		return builder.build();
	}
	
	@Test
	public void testGen() throws InvalidProtocolBufferException{
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("Before encode: " + req.toString());
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("After decode: " + req2.toString());
		System.out.println("Assert equal: " + req2.equals(req));
	}
	
	@Test
	public void showEncode(){
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		byte[] b = encode(req);
		for(byte a:b){
			System.out.print(a + "  ");
		}
	}
}
