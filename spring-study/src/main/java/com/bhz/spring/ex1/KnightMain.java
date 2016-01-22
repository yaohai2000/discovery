package com.bhz.spring.ex1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KnightMain {
	public static void main(String[] args){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("knights.xml");
		Knight knight = ctx.getBean(Knight.class);
		knight.embarkQuest();
		ctx.close();
	}
}
