package com.bhz.spring.ex3;

import java.math.BigDecimal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Discount {
	private static final BigDecimal discountRate = new BigDecimal("0.9");
	
	@Pointcut("execution(* com.bhz.spring.ex3.SaleService.sale(..)) && args(..)")
	private void perform(){}
	
	@Before("perform()")
	public void discountPrice(JoinPoint jp){
		if(jp.getArgs()[0] instanceof Product){
			Product p = (Product)jp.getArgs()[0];
			p.setPrice(p.getPrice().multiply(discountRate).setScale(2, BigDecimal.ROUND_UP));
		}
	}
}
