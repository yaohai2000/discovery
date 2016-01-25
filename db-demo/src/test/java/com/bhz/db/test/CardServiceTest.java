package com.bhz.db.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bhz.demo.service.CardService;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
public class CardServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private CardService cs;
	
	@Test
	public void testAdd(){
		cs.addCard("100", 0, "1800.90");
	}
}
