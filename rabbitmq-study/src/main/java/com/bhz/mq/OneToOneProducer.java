package com.bhz.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class OneToOneProducer {
	private final String QUEUE_NAME="test";
	private void sendMessage() throws IOException, TimeoutException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("172.16.26.160");
		Connection con = cf.newConnection();
		Channel channel = con.createChannel();
		
		channel.queueDeclare(QUEUE_NAME,true,false,false,null);
		channel.basicPublish("", QUEUE_NAME, null, "Hello World!".getBytes());
		
		System.out.println("Send message " + "Hello World!");
		
		channel.close();
		con.close();
	}
	
	public static void main(String[] args) throws Exception{
		OneToOneProducer a = new OneToOneProducer();
		a.sendMessage();
	}
}
