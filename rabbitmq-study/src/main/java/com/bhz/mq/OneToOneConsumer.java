package com.bhz.mq;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class OneToOneConsumer {
	private final String QUEUE_NAME="test";
	
	private void msgConsumer() throws Exception{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("192.168.1.206");
		cf.setUsername("yaoh");
		cf.setPassword("yaoh");
		Connection conn = cf.newConnection();
		Channel c = conn.createChannel();
		c.queueDeclare(QUEUE_NAME, true, false, false, null);
		Consumer consumer = new DefaultConsumer(c){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"UTF-8");
				System.out.println(msg);
			}
			
		};
		c.basicConsume(QUEUE_NAME, consumer);
	}
	
	public static void main(String[] args) throws Exception{
		OneToOneConsumer oc = new OneToOneConsumer();
		oc.msgConsumer();
	}
}
