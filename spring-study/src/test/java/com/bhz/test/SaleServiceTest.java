package com.bhz.test;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bhz.spring.ex3.Product;
import com.bhz.spring.ex3.SaleService;
import com.bhz.spring.ex3.SaleServiceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SaleServiceConfig.class})
public class SaleServiceTest {
	
	@Autowired
	private SaleService ss;
	
	@Test
	public void saleTest(){
		Product p = new Product();
		p.setId("1");
		p.setName("E1");
		p.setPrice(new BigDecimal("20.0"));
		ss.sale(p);
	}
}
