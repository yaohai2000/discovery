package com.bhz.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.bhz.spring.ex3.Product;
import com.bhz.spring.redis.RedisTemplateConfig;

public class RedisTest {

	
	@Test
	public void testInsert(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RedisTemplateConfig.class);
		RedisTemplate<String,Product> redis = ctx.getBean("redis",RedisTemplate.class);
		
		Product p = new Product();
		p.setId("A1");
		p.setName("Coin");
		p.setPrice(new BigDecimal("0.83"));
		redis.opsForValue().set(p.getName(), p);
	}
	
	@Test
	public void testShow(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RedisTemplateConfig.class);
		RedisTemplate<String,Product> redis = ctx.getBean("redis",RedisTemplate.class);
		Product p = redis.opsForValue().get("Coin");
		System.out.println(p);
	}
	
	@Test
	public void testAddList(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RedisTemplateConfig.class);
		RedisTemplate<String,Product> redis = ctx.getBean("redis",RedisTemplate.class);
		List<Product> pl = genProductList();
		for(Product p: pl){
			redis.opsForList().rightPush("product", p);
			System.out.println("Added Product [ "  + p + " ]");
		}
	}
	
	@Test
	public void testShowProducts(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RedisTemplateConfig.class);
		RedisTemplate<String,Product> redis = ctx.getBean("redis",RedisTemplate.class);
		List<Product> products = redis.opsForList().range("product", 0, 19);
		if(products==null || products.isEmpty()){
			System.out.println("There is no product on store");
		}else{
			for(Product p: products){
				System.out.println(p);
			}
		}
	}
	
	private List<Product> genProductList(){
		List<Product> l = new ArrayList<Product>();
		for(int i=0;i<20;i++){
			Product p = new Product();
			p.setId("B" + i);
			p.setName("ProductB" + i);
			p.setPrice(new BigDecimal("30.23"));
			l.add(p);
		}
		return l;
	}
}
