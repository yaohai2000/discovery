package com.bhz.db.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bhz.demo.entity.Card;
import com.bhz.demo.service.CardService;

public class MainTest {
	public static void main(String[] args){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"application-context.xml"});
		CardService cs = ac.getBean(CardService.class);
//		cs.addCard("100", 0, "2008.23");
		Card c = cs.getCard("100");
		System.out.println("Card No: " + c.getCardNo() + "Card Balance: " + c.getAmount());
	}
}
